package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.constant.ClanTagConstants;
import com.coc.data.constant.ClanWarConstants;
import com.coc.data.controller.vo.war.WarDetailVO;
import com.coc.data.controller.vo.war.WarLogVO;
import com.coc.data.dto.*;
import com.coc.data.dto.war.ClanWarLogDetailDTO;
import com.coc.data.enums.ClanWarStateEnum;
import com.coc.data.enums.ClanWarTypeEnum;
import com.coc.data.mapper.*;
import com.coc.data.model.base.*;
import com.coc.data.service.ClanService;
import com.coc.data.service.ClanWarService;
import com.coc.data.service.MiniProgramMessageService;
import com.coc.data.service.UserService;
import com.coc.data.util.DateUtil;
import com.coc.data.util.FormatUtil;
import com.coc.data.util.RedisKeyBuilder;
import com.coc.data.util.RedisUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author guokaiqiang
 * @date 2021/2/11 12:46
 */
@Service
@Slf4j
public class ClanWarServiceImpl implements ClanWarService {

    /**
     * mapper
     **/
    @Resource
    private ClanWarMapper clanWarMapper;
    @Resource
    private ClanWarMemberMapper clanWarMemberMapper;
    @Resource
    private ClanWarLogMapper clanWarLogMapper;
    @Resource
    private PlayerMapper playerMapper;
    @Resource
    private ClanMapper clanMapper;

    /**
     * service
     **/
    @Resource
    private MiniProgramMessageService miniProgramMessageService;
    @Resource
    private UserService userService;
    @Resource
    private ClanService clanService;


    /**
     * others
     **/
    @Resource
    private CocApiHttpClient httpClient;
    @Resource
    private RedisUtil redisUtil;

    private static final ThreadFactory CLAN_REFRESH_FACTORY =
        new ThreadFactoryBuilder().setNameFormat("clanRefresh-pool-%d").build();
    private static final ExecutorService CLAN_REFRESH_THREADS = new ThreadPoolExecutor(1, 1,
        0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000), CLAN_REFRESH_FACTORY, new ThreadPoolExecutor.AbortPolicy());

    @Override
    public void syncClanNormalWarInfo(String clanTag) {
        int count = clanWarMapper.getUnStartedClanWar(new Date(), clanTag);
        if (count > 0) {
            return;
        }
        try {
            WarInfoDTO currentWarInfo = httpClient.getClanCurrentWarInfoByClanTag(clanTag);
            if (ObjectUtils.isEmpty(currentWarInfo)) {
                log.error("部落标签{}, 获取当前对战信息失败。", clanTag);
                return;
            }
            if (ClanWarStateEnum.NO_IN.code.equals(currentWarInfo.getState())) {
                return;
            }
            currentWarInfo.setTag(getNormalWarTag(currentWarInfo.getPreparationStartTime(), currentWarInfo.getClan().getTag(), currentWarInfo.getOpponent().getTag()));
            currentWarInfo.setSeason(getWarSeason(currentWarInfo.getPreparationStartTime()));
            recNormalWarInfo(currentWarInfo);
            recWarMemberAndWarLogs(currentWarInfo);
        } catch (Exception e) {
            log.error("部落标签{}, 获取当前对战信息失败。", clanTag);
        }
    }

    @Override
    public void syncClanLeagueWarInfo(String warTag) {
        WarInfoDTO warInfo;
        try {
            warInfo = httpClient.getClanLeagueGroupWarInfoByTag(warTag);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }
        if (ObjectUtils.isEmpty(warInfo)) {
            log.error("联赛战争信息获取失败，战争标签:{}", warTag);
            return;
        }
        log.info("联赛战争信息获取成功，战争标签:{}", warTag);
        String season = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        warInfo.setSeason(season);
        warInfo.setTag(warTag);
        // 记录下对战信息
        log.info("更新战争信息");
        recLeagueWarInfo(warInfo, null);
        // 记录下对战详细信息
        log.info("更新对战详细信息");
        if (ClanWarConstants.WAR_ENDED.equals(warInfo.getState())) {
            log.info("战争已结束，更新参战人员");
            refreshLeagueGroupWarMembers(warInfo, warTag);
        }
        recWarMemberAndWarLogs(warInfo);
    }

    @Override
    public void syncClanLeagueWarInfos(List<ClanWar> clanWarList) {
        for (ClanWar clanWar : clanWarList) {
            syncClanLeagueWarInfo(clanWar.getTag());
        }
    }

    /**
     * 防止上场后被系统抓到，开战后又替换下去
     * @param warInfo
     * @param warTag
     * @return void
     * @author guokaiqiang
     * @date 2020/9/6 11:20
     **/
    private void refreshLeagueGroupWarMembers(WarInfoDTO warInfo, String warTag) {
        ClanWarInfoDTO clanWarInfo = warInfo.getClan();
        ClanWarInfoDTO opponentWarInfo = warInfo.getOpponent();
        List<String> clanWarMemberTagList = clanWarInfo.getMembers().stream().map(ClanWarMemberDTO::getTag).collect(Collectors.toList());
        clanWarMemberMapper.deleteNotInClanWarMember(warTag, clanWarInfo.getTag(), clanWarMemberTagList);
        // 刷新正方对战日志
        refreshLeagueGroupClanWarLogs(clanWarInfo.getTag(), warTag, opponentWarInfo, clanWarMemberTagList);

        clanWarMemberTagList = opponentWarInfo.getMembers().stream().map(ClanWarMemberDTO::getTag).collect(Collectors.toList());
        clanWarMemberMapper.deleteNotInClanWarMember(warTag, opponentWarInfo.getTag(), clanWarMemberTagList);
        // 刷新反方对战日志
        refreshLeagueGroupClanWarLogs(opponentWarInfo.getTag(), warTag, clanWarInfo, clanWarMemberTagList);
    }

    // 刷新对战日志，没有被打的，按照二星来计算
    private void refreshLeagueGroupClanWarLogs(String clanTag,
                                               String warTag,
                                               ClanWarInfoDTO opponentWarInfo,
                                               List<String> clanWarMemberTagList) {
        List<String> clanMemberTagsAttacked = Lists.newLinkedList();
        List<String> clanMemberTagsNoAttacked = Lists.newLinkedList();
        for (ClanWarMemberDTO member : opponentWarInfo.getMembers()) {
            if (ObjectUtils.isEmpty(member.getAttacks())) {
                continue;
            }
            for (AttackDTO attack : member.getAttacks()) {
                clanMemberTagsAttacked.add(attack.getDefenderTag());
            }
        }
        for (String tag : clanWarMemberTagList) {
            if (!clanMemberTagsAttacked.contains(tag)) {
                clanMemberTagsNoAttacked.add(tag);
            }
        }
        if (ObjectUtils.isEmpty(clanMemberTagsNoAttacked)) {
            return;
        }
        log.info("战争{}中，部落{}的成员{}没有被打过，增加计算", warTag, clanTag, FormatUtil.serializeObject2JsonStr(clanMemberTagsNoAttacked));
        List<AttackDTO> mockedAttackLogs = Lists.newLinkedList();
        for (String tag : clanMemberTagsNoAttacked) {
            mockedAttackLogs.add(AttackDTO.builder()
                .attackerTag(ClanTagConstants.MOCKED_ATTACKER_TAG)
                .defenderTag(tag)
                .destructionPercentage("50")
                .order(9999L)
                .stars(2L)
                .build());
        }
        for (ClanWarMemberDTO opponentMember : opponentWarInfo.getMembers()) {
            if (!ObjectUtils.isEmpty(opponentMember.getAttacks())) {
                opponentMember.getAttacks().addAll(mockedAttackLogs);
                break;
            }
        }
    }

    String getWarSeason(Date startTime) {
        LocalDate startTimeDate = DateUtil.asLocalDate(startTime);
        return String.format("%s-%s", startTimeDate.getYear(),
            startTimeDate.getMonthValue() > 9 ? startTimeDate.getMonthValue() :
                "0" + startTimeDate.getMonthValue());
    }

    String getNormalWarTag(Date preparationStartTime, String clanTag1, String clanTag2) {
        return FormatUtil.formatNormalWarTag(preparationStartTime, clanTag1, clanTag2);
    }

    @Override
    public void recNormalWarInfo(WarInfoDTO currentWarInfo) {
        recWarInfo(currentWarInfo, ClanWarTypeEnum.NORMAL.code, null);
    }

    @Override
    public void recLeagueWarInfo(WarInfoDTO currentWarInfo, String leagueTag) {
        recWarInfo(currentWarInfo, ClanWarTypeEnum.LEAGUE.code, leagueTag);
    }

    private void recWarInfo(WarInfoDTO warInfo, String type, String leagueTag) {
        ClanWar currentWar = ClanWar.builder()
            .clanTag(warInfo.getClan().getTag())
            .tag(warInfo.getTag())
            .season(warInfo.getSeason())
            .opponentClanTag(warInfo.getOpponent().getTag())
            .state(warInfo.getState())
            .teamSize(warInfo.getTeamSize().shortValue())
            .preparationStartTime(warInfo.getPreparationStartTime())
            .startTime(warInfo.getStartTime())
            .endTime(warInfo.getEndTime())
            .type(type)
            .build();

        if (leagueTag != null) {
            currentWar.setLeagueTag(leagueTag);
        }
        // 如果当前战争已经结束，判断是否需要发送战争消息
        if (ClanWarStateEnum.WAR_ENDED.code.equals(currentWar.getState())) {
            noticeWarEnd(warInfo);
        }
        CLAN_REFRESH_THREADS.submit(() -> {
           refreshClanInfo(warInfo.getClan().getTag());
           refreshClanInfo(warInfo.getOpponent().getTag());
        });

        clanWarMapper.insertOnDuplicateKeyUpdate(currentWar);
    }

    /**
     * 如果部落不在系统中，则同步到系统
     * @param clanTag
     * @return void
     * @author guokaiqiang
     * @date 2021/7/11 10:28
     **/
    private void refreshClanInfo(String clanTag) {
        if (!redisUtil.setnxWithMilliseconds(RedisKeyBuilder.buildClanInfoKey(clanTag), "1", 5 * 60 * 1000)) {
            return;
        }
        Clan clan = clanMapper.selectByClanTag(clanTag);
        if (clan == null) {
            clanService.syncClanBaseInfo(clanTag);
        }
    }

    private void noticeWarEnd(WarInfoDTO warInfo) {
        List<ClanWar> clanWars =
            clanWarMapper.getWarsByWarTagList(Collections.singletonList(warInfo.getTag()));
        if (ObjectUtils.isEmpty(clanWars)) {
            return;
        }
        ClanWar clanWar = clanWars.get(0);
        // 说明不是第一次捕捉到战争结束消息
        if (ClanWarStateEnum.WAR_ENDED.code.equals(clanWar.getState())) {
            return;
        }
        miniProgramMessageService.sendWarEndMessage(warInfo);
    }

    @Override
    public void recWarMemberAndWarLogs(WarInfoDTO warInfo) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        if (DateUtil.asDate(nowDateTime.plusMinutes(20)).after(warInfo.getStartTime())
            && DateUtil.asDate(nowDateTime.plusMinutes(15)).before(warInfo.getStartTime())
        ) {
            miniProgramMessageService.sendWarStartMessage(warInfo);
        }
        List<ClanWarMember> currentClanWarMemberDOList = Lists.newLinkedList();
        List<ClanWarLog> clanWarLogList = Lists.newLinkedList();
        ClanWarInfoDTO clanWithWarInfo = warInfo.getClan();
        ClanWarInfoDTO opponentClanWithWarInfo =  warInfo.getOpponent();

        log.info("部落 {}v.s.{} 正在处理参战成员及对战记录信息，战争标签：{}", clanWithWarInfo.getName(),
            opponentClanWithWarInfo.getName(), warInfo.getTag());
        List<ClanWarMemberDTO> clanWarMemberDTOList = clanWithWarInfo.getMembers();
        List<Player> playerList = Lists.newLinkedList();

        clanWarMemberDTOList.forEach(memberDTO -> {
            currentClanWarMemberDOList.add(ClanWarMember.builder()
                .memberTag(memberDTO.getTag())
                .memberName(memberDTO.getName())
                .mapPosition(memberDTO.getMapPosition().byteValue())
                .clanTag(clanWithWarInfo.getTag())
                .warTag(warInfo.getTag())
                .build()
            );
            playerList.add(Player.builder()
                .name(memberDTO.getName())
                .tag(memberDTO.getTag())
                .build());
            buildClanWarLog(warInfo, clanWarLogList, memberDTO);
        });
        List<ClanWarMemberDTO> opponentClanMemberDTOList = opponentClanWithWarInfo.getMembers();
        opponentClanMemberDTOList.forEach(memberDTO -> {
            currentClanWarMemberDOList.add(ClanWarMember.builder()
                .memberTag(memberDTO.getTag())
                .memberName(memberDTO.getName())
                .mapPosition(memberDTO.getMapPosition().byteValue())
                .clanTag(opponentClanWithWarInfo.getTag())
                .warTag(warInfo.getTag())
                .build()
            );
            playerList.add(Player.builder()
                .name(memberDTO.getName())
                .tag(memberDTO.getTag())
                .build());
            buildClanWarLog(warInfo, clanWarLogList, memberDTO);
        });
        if (!currentClanWarMemberDOList.isEmpty()) {
            log.info("部落 {}, 正在保存参战成员信息", clanWithWarInfo.getName());
            clanWarMemberMapper.batchInsert(currentClanWarMemberDOList);
            playerMapper.batchDelete(playerList);
            playerMapper.batchInsert(playerList);
        }
        if(!clanWarLogList.isEmpty()) {
            log.info("部落 {}, 正在保存对战记录信息", clanWithWarInfo.getName());
            clanWarLogMapper.batchInsert(clanWarLogList);
            miniProgramMessageService.sendThreeStarMessage(warInfo);
        }
    }

    @Override
    public void syncClanCurrentWarInfo(Clan clan) {
        // 没有报告需求的，直接不同步，返回
        if (!clan.getProvideClanWarReport()
            && !clan.getProvideLeagueWarReport()
        ) {
            return;
        }
        // 有未结束的联赛战争，就去获取联赛信息
        List<ClanWar> clanWars = clanWarMapper.getUnendedLeagueWarByClanTag(clan.getTag());
        if (!ObjectUtils.isEmpty(clanWars) && clan.getProvideLeagueWarReport()) {
            syncClanLeagueWarInfos(clanWars);
        } else if (clan.getProvideClanWarReport()) {
            syncClanNormalWarInfo(clan.getTag());
        }
    }

    @Override
    public WarDetailVO getWarDetail(String warTag, String clanTag) {
        List<ClanWarLogDetailDTO> clanWarLogList = clanWarLogMapper.getWarLogDetail(warTag);
        ClanWar clanWar = clanWarMapper.selectByWarTag(warTag);
        String opponentClanTag;
        if (clanTag.equals(clanWar.getClanTag())) {
            opponentClanTag = clanWar.getOpponentClanTag();
        } else {
            opponentClanTag = clanWar.getClanTag();
        }
        Clan clan = clanMapper.selectByClanTag(clanTag);
        ClanInfoDTO clanInfo = FormatUtil.deserializeCamelCaseJson2Object(clan.getExtraInfo(),
            ClanInfoDTO.class);
        Clan opponentClan = clanMapper.selectByClanTag(opponentClanTag);
        ClanInfoDTO opponentClanInfo =
            FormatUtil.deserializeCamelCaseJson2Object(opponentClan.getExtraInfo(), ClanInfoDTO.class);
        Map<String, List<ClanWarLogDetailDTO>> clanTagDetailMap =
            clanWarLogList.stream().collect(Collectors.groupingBy(ClanWarLogDetailDTO::getAttackerClanTag));
        List<WarLogVO> warLogVOList = Lists.newLinkedList();
        List<ClanWarLogDetailDTO> clanWarLogDetails = clanTagDetailMap.computeIfAbsent(clanTag,
            k -> Lists.newLinkedList());
        extractWarLogToVO(warLogVOList, clanWarLogDetails, true);

        List<ClanWarLogDetailDTO> opponentClanWarLogDetails =
            clanTagDetailMap.computeIfAbsent(opponentClanTag, k -> Lists.newLinkedList());
        extractWarLogToVO(warLogVOList, opponentClanWarLogDetails, false);
        int index = 0;
        List<WarLogVO> threeStarWarLogs = Lists.newLinkedList();
        for (WarLogVO warLogVO : warLogVOList) {
            warLogVO.setWxKey(String.valueOf(++index));
            // 我方半小时内的进攻三星
            if (warLogVO.getIsAttack()
                && warLogVO.getAttackStar() == 3
                && warLogVO.getCreateTime().after(DateUtil.asDate(LocalDateTime.now().minusMinutes(30)))
            ) {
                threeStarWarLogs.add(warLogVO);
            }
        }
        threeStarWarLogs =
            threeStarWarLogs.stream().sorted(Comparator.comparing(WarLogVO::getCreateTime).reversed()).collect(Collectors.toList());

        return WarDetailVO.builder()
            .clanIconUrl(clanInfo.getBadgeUrls().getSmall())
            .clanName(clanInfo.getName())
            .clanTag(clanInfo.getTag())
            .stars(clanWarLogDetails.stream().filter(s -> s.getIsBestAttack() != null && s.getIsBestAttack()).mapToLong(ClanWarLogDetailDTO::getStar).sum())
            .destructionPercentage(
                BigDecimal.valueOf(clanWarLogDetails.stream().filter(s -> s.getIsBestAttack() != null && s.getIsBestAttack()).mapToLong(ClanWarLogDetailDTO::getDestructionPercentage).sum())
                .divide(BigDecimal.valueOf(100 * clanWar.getTeamSize()), 4,
                    BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
            )
            .opponentStars(opponentClanWarLogDetails.stream().filter(s -> s.getIsBestAttack() != null && s.getIsBestAttack()).mapToLong(ClanWarLogDetailDTO::getStar).sum())
            .opponentDestructionPercentage(
                BigDecimal.valueOf(opponentClanWarLogDetails.stream().filter(s -> s.getIsBestAttack() != null && s.getIsBestAttack()).mapToLong(ClanWarLogDetailDTO::getDestructionPercentage).sum())
                    .divide(BigDecimal.valueOf(100 * clanWar.getTeamSize()), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
            )
            .opponentClanIconUrl(opponentClanInfo.getBadgeUrls().getSmall())
            .opponentClanName(opponentClanInfo.getName())
            .opponentClanTag(opponentClanInfo.getTag())
            .warTimeLeft(getWarTimeLeft(clanWar))
            .state(clanWar.getState())
            .stateMsg(ClanWarStateEnum.getEnumByCode(clanWar.getState()).msg)
            .warLogs(warLogVOList.stream().sorted(Comparator.comparing(WarLogVO::getCreateTime).reversed()).collect(Collectors.toList()))
            .recentThreeStarWarLogs(threeStarWarLogs)
            .build();
    }

    Long getWarTimeLeft(ClanWar clanWar) {
        Date date = null;
        // 准备日算开始时间
        if (ClanWarStateEnum.PREPARATION.code.equals(clanWar.getState())) {
            date = clanWar.getStartTime();
        } else if (ClanWarStateEnum.IN_WAR.code.equals(clanWar.getState())) {
            date = clanWar.getEndTime();
        }
        if (date == null) {
            return -1L;
        }
        return date.getTime() - System.currentTimeMillis();
    }

    private void extractWarLogToVO(List<WarLogVO> warLogVOList,
                                   List<ClanWarLogDetailDTO> clanWarLogDetails,
                                   Boolean isAttack) {
        Map<String, Long> bestAttackStar = Maps.newHashMap();
        Map<String, Long> bestAttackPercent = Maps.newHashMap();
        Map<String, Date> bestAttackTime = Maps.newHashMap();
        Map<String, ClanWarLogDetailDTO> bestWarLogDetail = Maps.newLinkedHashMap();
        clanWarLogDetails.forEach(warLogDetail -> {
            // 星星多，或者星星相等但摧毁率高，或星星相等，摧毁率也相等，但时间靠前
            if (bestAttackStar.computeIfAbsent(warLogDetail.getDefenderTag(), k -> 0L) < warLogDetail.getStar()
                ||
                (
                    bestAttackStar.get(warLogDetail.getDefenderTag()).equals(warLogDetail.getStar())
                        && bestAttackPercent.computeIfAbsent(warLogDetail.getDefenderTag(),
                        k -> 0L) < warLogDetail.getDestructionPercentage()
                )
                ||
                (
                    bestAttackStar.get(warLogDetail.getDefenderTag()).equals(warLogDetail.getStar())
                        && bestAttackPercent.get(warLogDetail.getDefenderTag()) < warLogDetail.getDestructionPercentage()
                    && bestAttackTime.get(warLogDetail.getDefenderTag()).after(warLogDetail.getCreateTime())
                )
            ) {
                 bestAttackPercent.put(warLogDetail.getDefenderTag(),
                     warLogDetail.getDestructionPercentage());
                 bestAttackStar.put(warLogDetail.getDefenderTag(), warLogDetail.getStar());
                 bestAttackTime.put(warLogDetail.getDefenderTag(), warLogDetail.getCreateTime());
                 bestWarLogDetail.put(warLogDetail.getDefenderTag(), warLogDetail);
            }

            warLogVOList.add(WarLogVO.builder()
                .attackerMapPosition(warLogDetail.getAttackerMapPosition())
                .attackerName(warLogDetail.getAttackerName())
                .attackerTag(warLogDetail.getAttackerTag())
                .defenderMapPosition(warLogDetail.getDefenderMapPosition())
                .defenderName(warLogDetail.getDefenderName())
                .defenderTag(warLogDetail.getDefenderTag())
                .timeAnchor(getWarLogTimeAnchor(warLogDetail.getCreateTime()))
                .createTime(warLogDetail.getCreateTime())
                .attackStar(warLogDetail.getStar())
                .destructionPercentage(warLogDetail.getDestructionPercentage())
                .isAttack(isAttack)
                .build());
        });
        bestWarLogDetail.forEach((k, warLogDetail) -> {
            warLogDetail.setIsBestAttack(true);
        });
    }

    String getWarLogTimeAnchor(Date createTime) {
        long millSeconds = System.currentTimeMillis() - createTime.getTime();
        // 多少分钟之前
        long minutes = millSeconds / 1000 / 60;
        if (minutes < 60) {
            return String.format("%s 分钟之前", minutes);
        }
        long hour = minutes / 60;

        return String.format("%s 小时之前", hour);
    }

    private void buildClanWarLog(WarInfoDTO warInfo, List<ClanWarLog> clanWarLogList, ClanWarMemberDTO memberDTO) {
        if (memberDTO.getAttacks() != null) {
            memberDTO.getAttacks().forEach(attack -> {
                clanWarLogList.add(
                    ClanWarLog.builder()
                        .attackerTag(attack.getAttackerTag())
                        .defenderTag(attack.getDefenderTag())
                        .destructionPercentage(attack.getDestructionPercentage())
                        .attackOrder(attack.getOrder().intValue())
                        .star(attack.getStars().shortValue())
                        .warTag(warInfo.getTag())
                        .build()
                );
            });
        }
    }
}
