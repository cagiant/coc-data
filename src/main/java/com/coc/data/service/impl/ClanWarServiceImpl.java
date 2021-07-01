package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.constant.ClanTagConstants;
import com.coc.data.constant.ClanWarConstants;
import com.coc.data.dto.*;
import com.coc.data.enums.ClanWarStateEnum;
import com.coc.data.enums.ClanWarTypeEnum;
import com.coc.data.mapper.*;
import com.coc.data.model.base.*;
import com.coc.data.service.ClanWarService;
import com.coc.data.service.MiniProgramMessageService;
import com.coc.data.service.UserService;
import com.coc.data.util.DateUtil;
import com.coc.data.util.FormatUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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

    /**
     * service
     **/
    @Resource
    private MiniProgramMessageService miniProgramMessageService;
    @Resource
    private UserService userService;


    /**
     * others
     **/
    @Resource
    private CocApiHttpClient httpClient;

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
            recNormalWarInfo(currentWarInfo, clanTag);
            recWarMemberAndWarLogs(currentWarInfo, clanTag);
        } catch (Exception e) {
            log.error("部落标签{}, 获取当前对战信息失败。", clanTag);
        }
    }

    @Override
    public void syncClanLeagueWarInfo(String warTag, String clanTag) {
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
        recLeagueWarInfo(warInfo, clanTag, null);
        // 记录下对战详细信息
        log.info("更新对战详细信息");
        if (ClanWarConstants.WAR_ENDED.equals(warInfo.getState())) {
            log.info("战争已结束，更新参战人员");
            refreshLeagueGroupWarMembers(warInfo, warTag);
        }
        recWarMemberAndWarLogs(warInfo, clanTag);
    }

    @Override
    public void syncClanLeagueWarInfos(List<ClanWar> clanWarList, String clanTag) {
        for (ClanWar clanWar : clanWarList) {
            syncClanLeagueWarInfo(clanWar.getTag(), clanTag);
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
    public void recNormalWarInfo(WarInfoDTO currentWarInfo, String clanTag) {
        recWarInfo(currentWarInfo, clanTag, ClanWarTypeEnum.NORMAL.code, null);
    }

    @Override
    public void recLeagueWarInfo(WarInfoDTO currentWarInfo, String clanTag, String leagueTag) {
        recWarInfo(currentWarInfo, clanTag, ClanWarTypeEnum.LEAGUE.code, leagueTag);
    }

    private void recWarInfo(WarInfoDTO warInfo, String clanTag, String type, String leagueTag) {
        ClanWar currentWar = ClanWar.builder()
            .clanTag(clanTag)
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

        clanWarMapper.insertOnDuplicateKeyUpdate(currentWar);
    }

    @Override
    public void recWarMemberAndWarLogs(WarInfoDTO warInfo, String clanTag) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        if (DateUtil.asDate(nowDateTime.minusMinutes(20)).before(warInfo.getStartTime())
            && DateUtil.asDate(nowDateTime.minusMinutes(15)).after(warInfo.getStartTime())
        ) {
            miniProgramMessageService.sendWarStartMessage(warInfo);
        }
        List<ClanWarMember> currentClanWarMemberDOList = Lists.newLinkedList();
        List<ClanWarLog> clanWarLogList = Lists.newLinkedList();
        ClanWarInfoDTO clanWithWarInfo;
        ClanWarInfoDTO opponentClanWithWarInfo;
        if (clanTag.equals(warInfo.getClan().getTag())) {
            clanWithWarInfo = warInfo.getClan();
            opponentClanWithWarInfo = warInfo.getOpponent();
        } else {
            clanWithWarInfo = warInfo.getOpponent();
            opponentClanWithWarInfo = warInfo.getClan();
        }
        log.info("部落 {}, 正在处理参战成员及对战记录信息，战争标签：{}", clanWithWarInfo.getName(), warInfo.getTag());
        List<ClanWarMemberDTO> clanWarMemberDTOList = clanWithWarInfo.getMembers();
        List<Player> playerList = Lists.newLinkedList();

        clanWarMemberDTOList.forEach(memberDTO -> {
            currentClanWarMemberDOList.add(ClanWarMember.builder()
                .memberTag(memberDTO.getTag())
                .memberName(memberDTO.getName())
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
        if (clan.getProvideClanWarReport() == 0
            && clan.getProvideLeagueWarReport() == 0
        ) {
            return;
        }
        // 有未结束的联赛战争，就去获取联赛信息
        List<ClanWar> clanWars = clanWarMapper.getUnendedLeagueWarByClanTag(clan.getTag());
        if (!ObjectUtils.isEmpty(clanWars) && clan.getProvideLeagueWarReport() == 1) {
            syncClanLeagueWarInfos(clanWars, clan.getTag());
        } else if (clan.getProvideClanWarReport() == 1) {
            syncClanNormalWarInfo(clan.getTag());
        }
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
