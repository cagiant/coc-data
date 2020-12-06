package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.client.ProxyHttpClient;
import com.coc.data.constant.ClanTagConstants;
import com.coc.data.constant.ClanWarConstants;
import com.coc.data.dto.*;
import com.coc.data.mapper.*;
import com.coc.data.model.*;
import com.coc.data.service.DataSyncService;
import com.coc.data.util.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author guokaiqiang
 * @date 2020/7/30 22:02
 */
@Service("dataSyncService")
@Slf4j
public class DataSyncServiceImpl implements DataSyncService {

    @Resource
    private CocApiHttpClient httpClient;

    @Resource
    private ClansMapper clansMapper;
    @Resource
    private ClanMembersMapper clanMembersMapper;
    @Resource
    private ClanWarsMapper clanWarsMapper;
    @Resource
    private ClanWarMembersMapper clanWarMembersMapper;
    @Resource
    private ClanWarLogsMapper clanWarLogsMapper;
    @Resource
    private ReportClanWarMemberMapper reportClanWarMemberMapper;
    @Resource
    private PlayersMapper playersMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncClanInfo() {
        List<String> clanTags = Arrays.asList(ClanTagConstants.WHITE_LIST_TAGS.split(","));
        for (String clanTag : clanTags) {
            ClanInfoDTO clanInfo = httpClient.getClanInfoByTag(clanTag);
            Clans clan = Clans.builder()
                .name(clanInfo.getName())
                .tag(clanInfo.getTag())
                .build();
            clansMapper.insertOnDuplicateKeyUpdate(clan);
            List<ClanMemberDTO> clanMemberDTOList = clanInfo.getMemberList();
            List<ClanMembers> clanMembersList = new ArrayList<>();
            List<Players> playersList = new ArrayList<>();
            clanMemberDTOList.forEach(clanMemberDTO -> {
                clanMembersList.add(ClanMembers.builder()
                    .clanTag(clanInfo.getTag())
                    .tag(clanMemberDTO.getTag())
                    .name(clanMemberDTO.getName())
                    .build()
                );
                playersList.add(Players.builder()
                    .name(clanMemberDTO.getName())
                    .tag(clanMemberDTO.getTag())
                    .build());
            });
            clanMembersMapper.insertOnDuplicateKeyUpdate(clanMembersList);
            playersMapper.insertOnDuplicateKeyUpdate(playersList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncClanCurrentWarInfo() {
        List<String> clanTags = Arrays.asList(ClanTagConstants.WHITE_LIST_TAGS.split(","));
        for (String clanTag : clanTags) {
            ClanWars clanWar = clanWarsMapper.getUnStartedClanWar(new Date(), clanTag);
            if (!ObjectUtils.isEmpty(clanWar) && clanWar.getIsLeagueWar() == 0) {
                log.info("部落标签:{}, 有处于准备日的战争: {}, 战争开始时间为:{}. 先跳过...",
                        clanWar.getClanTag(),
                        clanWar.getTag(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(clanWar.getStartTime()));
                continue;
            }
            log.info("部落标签 {}, 开始获取当前对战信息", clanTag);
            WarInfoDTO currentWarInfo = httpClient.getClanCurrentWarInfoByClanTag(clanTag);
            if (ClanWarConstants.NOT_IN_WAR.equals(currentWarInfo.getState())) {
                log.info("对战信息获取结束，部落:{}没有日常部落战",clanTag);
                continue;
            }
            if (ObjectUtils.isEmpty(currentWarInfo)) {
                log.error("部落标签{}, 获取当前对战信息失败。", clanTag);
                continue;
            }
            log.info("部落标签 {}, 获取当前对战信息完毕。 对应部落名称 {}", clanTag, currentWarInfo.getClan().getName());
            String currentWarTag = new SimpleDateFormat("yyyy-MM-dd").format(currentWarInfo.getPreparationStartTime());
            currentWarInfo.setTag(currentWarTag);
            currentWarInfo.setSeason(new SimpleDateFormat("yyyy-MM").format(currentWarInfo.getStartTime()));
            // 记录下战争信息
            recWarInfo(currentWarInfo, clanTag, (byte) 0);
            // 记下参战成员及对战记录
            recWarMemberAndWarLogs(currentWarInfo, clanTag, 2L);
        }
    }

    @Override
    public void syncLeagueGroupInfo() {
        List<Clans> clansList = clansMapper.getClansNeedLeagueReport();
        for (Clans clan: clansList) {
            log.info("开始获取部落 {} 的联赛信息",clan.getName());
            LeagueGroupInfoDTO leagueGroupInfo;
            try {
                leagueGroupInfo = httpClient.getClanLeagueGroupInfoByClanTag(clan.getTag());
            } catch (Exception e) {
                log.info("{} 部落没有进行中的联赛", clan.getName());
                log.error(e.getMessage(),e);
                continue;
            }
            log.info("获取部落 {} 的联赛信息完毕",clan.getName());
            List<LeagueGroupRoundDTO> rounds = leagueGroupInfo.getRounds();
            for (LeagueGroupRoundDTO round : rounds) {
                List<String> warTags = round.getWarTags();
                if (ClanTagConstants.DEFAULT_LEAGUE_GROUP_WAR_TAG.equals(warTags.get(0))) {
                    continue;
                }
                List<ClanWars> warList = clanWarsMapper.getWarsByWarTagList(warTags);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (!ObjectUtils.isEmpty(warList)) {
                    ClanWars war = warList.get(0);
                    log.info(
                        "战争 {} 已经被同步，开始时间{}， 结束时间{},状态：{}",
                        war.getTag(),
                        dateFormat.format(war.getStartTime()),
                        dateFormat.format(war.getEndTime()),
                        war.getState()
                    );
                    continue;
                }
                WarInfoDTO warInfo = getLeagueGroupWarBelongsToClan(warTags, clan.getTag());
                warInfo.setSeason(leagueGroupInfo.getSeason());
                if (ObjectUtils.isEmpty(warInfo)) {
                    continue;
                }
                // 先记录下对战信息
                recWarInfo(warInfo,  clan.getTag(), (byte) 1);
                // 先记录下对战详细信息
                recWarMemberAndWarLogs(warInfo, clan.getTag(),1L);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void syncLeagueGroupWarInfo() {
        String season = new SimpleDateFormat("yyyy-MM").format(new Date());
        List<ClanWars> leagueWarList = clanWarsMapper.getUnEndedLeagueWar(season);
        if (ObjectUtils.isEmpty(leagueWarList)) {
            log.info("目前没有未结束的联赛战争");
            return;
        }
        for (ClanWars clanWar : leagueWarList) {
            WarInfoDTO warInfo;
            try {
                 warInfo = httpClient.getClanLeagueGroupWarInfoByTag(clanWar.getTag());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                continue;
            }
            if (ObjectUtils.isEmpty(warInfo)) {
                log.error("联赛战争信息获取失败，战争标签:{},部落标签{}", clanWar.getTag(), clanWar.getClanTag());
                continue;
            }
            log.info("联赛战争信息获取成功，战争标签:{},部落标签{}", clanWar.getTag(), clanWar.getClanTag());
            warInfo.setSeason(season);
            warInfo.setTag(clanWar.getTag());
            // 记录下对战信息
            log.info("更新战争信息");
            recWarInfo(warInfo, clanWar.getClanTag(), (byte) 1);
            // 记录下对战详细信息
            log.info("更新对战详细信息");
            if (ClanWarConstants.WAR_ENDED.equals(warInfo.getState())) {
                log.info("战争已结束，更新参战人员");
                refreshLeagueGroupWarMembers(warInfo, clanWar.getClanTag());
            }
            recWarMemberAndWarLogs(warInfo, clanWar.getClanTag(),1L);
        }
    }

    /**
     * 防止上场后被系统抓到，开战后又替换下去
     * @param warInfo
     * @param clanTag
     * @return void
     * @author guokaiqiang
     * @date 2020/9/6 11:20
     **/
    private void refreshLeagueGroupWarMembers(WarInfoDTO warInfo, String clanTag) {
        ClanWarInfoDTO clanWarInfo;
        ClanWarInfoDTO opponentWarInfo;
        if (clanTag.equals(warInfo.getClan().getTag())) {
            clanWarInfo = warInfo.getClan();
            opponentWarInfo = warInfo.getOpponent();
        } else {
            clanWarInfo = warInfo.getOpponent();
            opponentWarInfo = warInfo.getClan();
        }

        List<ClanWarMemberDTO> clanWarMemberList = clanWarInfo.getMembers();
        List<String> clanWarMemberTagList = clanWarMemberList.stream().map(ClanWarMemberDTO::getTag).collect(Collectors.toList());
        clanWarMembersMapper.deleteNotInClanWarMember(warInfo.getTag(), clanTag, clanWarMemberTagList);

        // 没有被打的，按照二星来计算
        List<String> clanMemberTagsAttacked = new ArrayList<>();
        List<String> clanMemberTagsNoAttacked = new ArrayList<>();
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
        log.info("战争{}中，部落{}的成员{}没有被打过，增加计算", warInfo.getTag(), clanTag, FormatUtil.serializeObject2JsonStr(clanMemberTagsNoAttacked));
        List<AttackDTO> mockedAttackLogs = new ArrayList<>();
        for (String tag : clanMemberTagsNoAttacked) {
            mockedAttackLogs.add(AttackDTO.builder()
                .attackerTag(ClanTagConstants.MOCKED_ATTACKER_TAG)
                .defenderTag(tag)
                .destructionPercentage("50")
                .order(9999L)
                .stars(2L)
                .build());
        }
        opponentWarInfo.getMembers().get(0).getAttacks().addAll(mockedAttackLogs);
    }

    /**
     * 从一众对战信息中选出我们需要的那个
     * @param warTags
     * @param clanTag
     * @return com.coc.data.dto.WarInfoDTO
     * @author guokaiqiang
     * @date 2020/9/5 22:28
     **/
    private WarInfoDTO getLeagueGroupWarBelongsToClan(List<String> warTags, String clanTag) {
        for (String warTag : warTags) {
            WarInfoDTO warInfo = httpClient.getClanLeagueGroupWarInfoByTag(warTag);
            if (
                clanTag.equals(warInfo.getClan().getTag())
                || clanTag.equals(warInfo.getOpponent().getTag())
            ) {
                warInfo.setTag(warTag);
                return warInfo;
            }
        }

        return null;
    }

    /**
     * 记下对战成员和对战日志
     * @param warInfo
     * @param clanTag
     * @param attachTimeLeft
     * @return void
     * @author guokaiqiang
     * @date 2020/9/6 10:41
     **/
    private void recWarMemberAndWarLogs(WarInfoDTO warInfo, String clanTag, Long attachTimeLeft) {
        List<ClanWarMembers> currentClanWarMemberDOList = new ArrayList<>();
        ClanWarInfoDTO clanWarInfo;
        ClanWarInfoDTO opponentWarInfo;
        if (clanTag.equals(warInfo.getClan().getTag())) {
            clanWarInfo = warInfo.getClan();
            opponentWarInfo = warInfo.getOpponent();
        } else {
            clanWarInfo = warInfo.getOpponent();
            opponentWarInfo = warInfo.getClan();
        }
        String season = new SimpleDateFormat("yyyy-MM").format(warInfo.getStartTime());
        List<ClanWarMemberDTO> clanWarMemberDTOList = clanWarInfo.getMembers();
        List<ClanWarLogs> clanWarLogList = new ArrayList<>(100);
        log.info("部落 {}, 正在处理参战成员及对战记录信息，战争标签：{}", clanWarInfo.getName(), warInfo.getTag());
        clanWarMemberDTOList.forEach(memberDTO -> {
            currentClanWarMemberDOList.add(ClanWarMembers.builder()
                .clanTag(clanTag)
                .memberTag(memberDTO.getTag())
                .memberName(memberDTO.getName())
                .season(season)
                .warTag(warInfo.getTag())
                .attackTimeLeft(attachTimeLeft)
                .leagueWar(attachTimeLeft.intValue() == 1)
                .build()
            );
            if (memberDTO.getAttacks() != null) {
                memberDTO.getAttacks().forEach(attack -> {
                    clanWarLogList.add(
                        ClanWarLogs.builder()
                            .attackerTag(attack.getAttackerTag())
                            .defenderTag(attack.getDefenderTag())
                            .destructionPercentage(attack.getDestructionPercentage())
                            .attackOrder(attack.getOrder())
                            .star(attack.getStars())
                            .warTag(warInfo.getTag())
                            .clanTag(clanTag)
                            .build()
                    );
                });
            }
        });
        List<ClanWarMemberDTO> opponentClanMemberDTOList = opponentWarInfo.getMembers();
        opponentClanMemberDTOList.forEach(memberDTO -> {
            if (memberDTO.getAttacks() != null) {
                memberDTO.getAttacks().forEach(attack -> {
                    clanWarLogList.add(
                        ClanWarLogs.builder()
                            .attackerTag(attack.getAttackerTag())
                            .defenderTag(attack.getDefenderTag())
                            .destructionPercentage(attack.getDestructionPercentage())
                            .attackOrder(attack.getOrder())
                            .star(attack.getStars())
                            .warTag(warInfo.getTag())
                            .clanTag(clanTag)
                            .build()
                    );
                });
            }
        });
        if (!currentClanWarMemberDOList.isEmpty()) {
            log.info("部落 {}, 正在保存参战成员信息", clanWarInfo.getName());
            clanWarMembersMapper.insertIgnoreExist(currentClanWarMemberDOList);
        }
        if(!clanWarLogList.isEmpty()) {
            log.info("部落 {}, 正在保存对战记录信息", clanWarInfo.getName());
            clanWarLogsMapper.insertIgnoreExist(clanWarLogList);
        }
    }

    /**
     * 记下当前对战信息
     * @param warInfo
     * @param clanTag
     * @param isLeagaueWar
     * @return void
     * @author guokaiqiang
     * @date 2020/9/6 10:42
     **/
    private void recWarInfo(WarInfoDTO warInfo,String clanTag, byte isLeagaueWar) {
        ClanWars currentWar = ClanWars.builder()
            .clanTag(clanTag)
            .tag(warInfo.getTag())
            .season(warInfo.getSeason())
            .state(warInfo.getState())
            .teamSize(warInfo.getTeamSize())
            .preparationStartTime(warInfo.getPreparationStartTime())
            .startTime(warInfo.getStartTime())
            .endTime(warInfo.getEndTime())
            .isLeagueWar(isLeagaueWar)
            .build();

        clanWarsMapper.insertOnDuplicateKeyUpdate(currentWar);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void calculateClanWarLogs() {
        List<ClanWarLogs> warLogList = clanWarLogsMapper.getUncalculatedClanWarLogs();
        if (!warLogList.isEmpty()) {
            log.info("将未处理的对战信息归档...");
            for (ClanWarLogs warLog : warLogList) {
                addLogToClanWarMember(warLog);
            }
            log.info("归档完毕");
        }
    }

    @Override
    public void generateSeasonReports() {
        String season = new SimpleDateFormat("yyyy-MM").format(new Date());
        log.info("正在生成赛季 {} 的报表", season);
        List<ClanWarMembers> clanWarMembersList = clanWarMembersMapper.getCurrentSeasonData(season);
        List<ReportClanWarMember> reportClanWarMemberList = new ArrayList<>();
        List<ClanWarMembers> reportClanWarMemberListTmp = new ArrayList<>();
        if (clanWarMembersList.size() == 0) {
            return;
        }
        clanWarMembersList.forEach(clanWarMember -> {
            reportClanWarMemberList.add(ReportClanWarMember.builder()
                .attackNoStarTime(clanWarMember.getAttackNoStarTime())
                .attackOneStarTime(clanWarMember.getAttackOneStarTime())
                .attackTwoStarTime(clanWarMember.getAttackTwoStarTime())
                .attackThreeStarTime(clanWarMember.getAttackThreeStarTime())
                .defenseNoStarTime(clanWarMember.getDefenseNoStarTime())
                .defenseOneStarTime(clanWarMember.getDefenseOneStarTime())
                .defenseTwoStarTime(clanWarMember.getDefenseTwoStarTime())
                .defenseThreeStarTime(clanWarMember.getDefenseThreeStarTime())
                .attackTimeLeft(clanWarMember.getAttackTimeLeft())
                .attackTimeUsed(clanWarMember.getAttackTimeUsed())
                .totalAttackStar(clanWarMember.getTotalAttackStar())
                .totalDefenseStar(clanWarMember.getTotalDefenseStar())
                .totalDefenseTime(clanWarMember.getTotalDefenseTime())
                .season(season)
                .leagueWar(clanWarMember.getLeagueWar())
                .memberTag(clanWarMember.getMemberTag())
                .clanTag(clanWarMember.getClanTag())
                .build()
            );
            reportClanWarMemberListTmp.add(ClanWarMembers.builder()
                .seasonallyReport(true)
                .memberName(clanWarMember.getMemberName())
                .attackNoStarTime(clanWarMember.getAttackNoStarTime())
                .attackOneStarTime(clanWarMember.getAttackOneStarTime())
                .attackTwoStarTime(clanWarMember.getAttackTwoStarTime())
                .attackThreeStarTime(clanWarMember.getAttackThreeStarTime())
                .defenseNoStarTime(clanWarMember.getDefenseNoStarTime())
                .defenseOneStarTime(clanWarMember.getDefenseOneStarTime())
                .defenseTwoStarTime(clanWarMember.getDefenseTwoStarTime())
                .defenseThreeStarTime(clanWarMember.getDefenseThreeStarTime())
                .attackTimeLeft(clanWarMember.getAttackTimeLeft())
                .attackTimeUsed(clanWarMember.getAttackTimeUsed())
                .totalAttackStar(clanWarMember.getTotalAttackStar())
                .totalDefenseStar(clanWarMember.getTotalDefenseStar())
                .totalDefenseTime(clanWarMember.getTotalDefenseTime())
                .totalAttackPercentage(clanWarMember.getTotalAttackPercentage())
                .totalDefensePercentage(clanWarMember.getTotalDefensePercentage())
                .season(season)
                .warTag(ClanTagConstants.MOCKED_WAR_TAG)
                .leagueWar(clanWarMember.getLeagueWar())
                .memberTag(clanWarMember.getMemberTag())
                .clanTag(clanWarMember.getClanTag())
                .build());
        });

        reportClanWarMemberMapper.replaceBatch(reportClanWarMemberList);
        clanWarMembersMapper.replaceSeasonReport(reportClanWarMemberListTmp);

    }

    private void addLogToClanWarMember(ClanWarLogs warLog) {
        String attackerTag = warLog.getAttackerTag();
        String defenderTag = warLog.getDefenderTag();
        String warTag = warLog.getWarTag();
        String clanTag = warLog.getClanTag();
        List<ClanWarMembers> relatedMemberList = clanWarMembersMapper.getWarLogRelatedClanWarMembers(
            attackerTag, defenderTag, warTag, clanTag);
        for(ClanWarMembers clanWarMember : relatedMemberList) {
            // 是进攻方，记录下进攻星星
            if (attackerTag.equals(clanWarMember.getMemberTag())) {
                clanWarMember.setTotalAttackStar(clanWarMember.getTotalAttackStar() + warLog.getStar());
                switch (warLog.getStar().byteValue()) {
                    case 0:
                        clanWarMember.setAttackNoStarTime(clanWarMember.getAttackNoStarTime() + 1);
                        break;
                    case 1:
                        clanWarMember.setAttackOneStarTime(clanWarMember.getAttackOneStarTime() + 1);
                        break;
                    case 2:
                        clanWarMember.setAttackTwoStarTime(clanWarMember.getAttackTwoStarTime() + 1);
                        break;
                    case 3:
                        clanWarMember.setAttackThreeStarTime(clanWarMember.getAttackThreeStarTime() + 1);
                        break;
                    default:
                        log.error("unrecognized war log {}", warLog);
                }
                clanWarMember.setAttackTimeUsed(clanWarMember.getAttackTimeUsed() + 1);
                clanWarMember.setAttackTimeLeft(clanWarMember.getAttackTimeLeft() - 1);
                clanWarMember.setTotalAttackPercentage(clanWarMember.getTotalAttackPercentage() + Long.parseLong(warLog.getDestructionPercentage()));
            }
            // 防守方，记录下被三次数
            if (
                defenderTag.equals(clanWarMember.getMemberTag())
            ) {
                Long totalDefenseStar = 0L;
                // 联赛的，只看最高的那次防守星星
                if (clanWarMember.getLeagueWar()) {
                    totalDefenseStar = Math.max(clanWarMember.getTotalDefenseStar(), warLog.getStar());
                } else {
                    totalDefenseStar = clanWarMember.getTotalDefenseStar() + warLog.getStar();
                }
                clanWarMember.setTotalDefenseStar(totalDefenseStar);
                switch (warLog.getStar().byteValue()) {
                    case 0:
                        clanWarMember.setDefenseNoStarTime(clanWarMember.getDefenseNoStarTime() + 1);
                        break;
                    case 1:
                        clanWarMember.setDefenseOneStarTime(clanWarMember.getDefenseOneStarTime() + 1);
                        break;
                    case 2:
                        clanWarMember.setDefenseTwoStarTime(clanWarMember.getDefenseTwoStarTime() + 1);
                        break;
                    case 3:
                        clanWarMember.setDefenseThreeStarTime(clanWarMember.getDefenseThreeStarTime() + 1);
                        break;
                    default:
                        log.error("unrecognized war log {}", FormatUtil.serializeObject2JsonStr(warLog));
                }
                clanWarMember.setTotalDefenseTime(clanWarMember.getTotalDefenseTime() + 1);
                clanWarMember.setTotalDefensePercentage(clanWarMember.getTotalDefensePercentage() + Long.parseLong(warLog.getDestructionPercentage()));
            }
            clanWarMembersMapper.updateClanWarMember(clanWarMember);
          }
          clanWarLogsMapper.setClanWarLogCalaulated(warLog.getId());    
    }
}
