package com.coc.data.service.impl;

import com.coc.data.client.ProxyHttpClient;
import com.coc.data.constant.ClanTagConstants;
import com.coc.data.dto.*;
import com.coc.data.mapper.*;
import com.coc.data.model.*;
import com.coc.data.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author guokaiqiang
 * @date 2020/7/30 22:02
 */
@Service("dataSyncService")
@Slf4j
public class DataSyncServiceImpl implements DataSyncService {

    @Resource
    private ProxyHttpClient httpClient;

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
            clanMemberDTOList.forEach(clanMemberDTO -> {
                clanMembersList.add(ClanMembers.builder()
                    .clanTag(clanInfo.getTag())
                    .tag(clanMemberDTO.getTag())
                    .name(clanMemberDTO.getName())
                    .build()
                );
            });
            clanMembersMapper.insertOnDuplicateKeyUpdate(clanMembersList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncClanCurrentWarInfo() {
        List<String> clanTags = Arrays.asList(ClanTagConstants.WHITE_LIST_TAGS.split(","));
        for (String clanTag : clanTags) {
            ClanWars clanWar = clanWarsMapper.getUnStartedClanWar(new Date(), clanTag);
            if (!ObjectUtils.isEmpty(clanWar) && clanWar.getIsLeagueWar() == 0) {
                log.warn("部落标签:{}, 有处于准备日的战争: {}, 战争开始时间为:{}. 先跳过...",
                        clanWar.getClanTag(),
                        clanWar.getTag(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(clanWar.getStartTime()));
                continue;
            }
            log.info("部落标签 {}, 开始获取当前对战信息", clanTag);
            WarInfoDTO currentWarInfo = httpClient.getClanCurrentWarInfoByClanTag(clanTag);
            log.info("部落标签 {}, 获取当前对战信息完毕。 对应部落名称 {}", clanTag, currentWarInfo.getClan().getName());
            String currentWarTag = new SimpleDateFormat("yyyy-MM-dd").format(currentWarInfo.getPreparationStartTime());
            currentWarInfo.setTag(currentWarTag);
            currentWarInfo.setSeason(new SimpleDateFormat("yyyy-MM").format(currentWarInfo.getStartTime()));
            // 记录下战争信息
            recWarInfo(currentWarInfo, (byte) 0);
            // 记下参战成员及对战记录
            recWarMemberAndWarLogs(currentWarInfo, clanTag, 2L);
        }
    }

    @Override
    public void syncLeagueGroupInfo() {
        List<Clans> clansList = clansMapper.getClansNeedLeagueReport();
        clansList.forEach(clan -> {
            log.info("正在获取部落 {} 的联赛信息",clan.getName());
            LeagueGroupInfoDTO leagueGroupInfo = httpClient.getClanLeagueGroupInfoByClanTag(clan.getTag());
            log.info("正在获取部落 {} 的联赛信息完毕",clan.getName());
            List<LeagueGroupRoundDTO> rounds = leagueGroupInfo.getRounds();
            for (LeagueGroupRoundDTO round : rounds) {
                List<String> warTags = round.getWarTags();
                if (ClanTagConstants.DEFAULT_LEAGUE_GROUP_WAR_TAG.equals(warTags.get(0))) {
                    continue;
                }
                List<ClanWars> warList = clanWarsMapper.getWarsByWarTagList(warTags);
                if (!ObjectUtils.isEmpty(warList)) {
                    ClanWars war = warList.get(0);
                    log.info("战争 {} 已经被同步，开始时间{}， 结束时间{},状态：{}", war.getTag(), war.getStartTime(), war.getEndTime(), war.getState());
                    continue;
                }
                WarInfoDTO warInfo = getLeagueGroupWarBelongsToClan(warTags, clan.getTag());
                warInfo.setSeason(leagueGroupInfo.getSeason());
                if (ObjectUtils.isEmpty(warInfo)) {
                    continue;
                }
                // 先记录下对战信息
                recWarInfo(warInfo,  (byte) 1);
                // 先记录下对战详细信息
                recWarMemberAndWarLogs(warInfo, clan.getTag(),1L);
            }
        });
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
        List<ClanWarMemberDTO> clanWarMemberDTOList = clanWarInfo.getMembers();
        List<ClanWarLogs> clanWarLogList = new ArrayList<>(100);
        log.info("部落 {}, 正在处理参战成员及对战记录信息", clanWarInfo.getName());
        clanWarMemberDTOList.forEach(memberDTO -> {
            currentClanWarMemberDOList.add(ClanWarMembers.builder()
                .clanTag(clanTag)
                .memberTag(memberDTO.getTag())
                .memberName(memberDTO.getName())
                .warTag(warInfo.getTag())
                .attackTimeLeft(attachTimeLeft)
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

    private void recWarInfo(WarInfoDTO warInfo, byte isLeagaueWar) {
        ClanWars currentWar = ClanWars.builder()
            .clanTag(warInfo.getClan().getTag())
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
        List<ClanWarMembers> clanWarMembersList = clanWarMembersMapper.getCurrentSeasonData(season);
        List<ReportClanWarMember> reportClanWarMemberList = new ArrayList<>();
        if (clanWarMembersList.size() == 0) {
            return;
        }
        clanWarMembersList.forEach(clanWarMember -> {
            reportClanWarMemberList.add(ReportClanWarMember.builder()
                .attackNoStarTime(clanWarMember.getAttackNoStarTime())
                .attackOneStarTime(clanWarMember.getAttackOneStarTime())
                .attackTwoStarTime(clanWarMember.getAttackTwoStarTime())
                .attackThreeStarTime(clanWarMember.getAttackThreeStarTime())
                .defenseThreeStarTime(clanWarMember.getDefenseThreeStarTime())
                .attackTimeLeft(clanWarMember.getAttackTimeLeft())
                .attackTimeUsed(clanWarMember.getAttackTimeUsed())
                .season(season)
                .memberTag(clanWarMember.getMemberTag())
                .clanTag(clanWarMember.getClanTag())
                .build()
            );
        });

        reportClanWarMemberMapper.replaceBatch(reportClanWarMemberList);

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
            }
            // 防守方，记录下被三次数
            if (
                defenderTag.equals(clanWarMember.getMemberTag())
                && warLog.getStar().byteValue() == 3
            ) {
                clanWarMember.setDefenseThreeStarTime(clanWarMember.getDefenseThreeStarTime() + 1);
            }
            clanWarMembersMapper.updateClanWarMember(clanWarMember);
            clanWarLogsMapper.setClanWarLogCalaulated(warLog.getId());
        }
    }
}
