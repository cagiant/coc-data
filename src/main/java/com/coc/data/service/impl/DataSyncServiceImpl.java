package com.coc.data.service.impl;

import com.coc.data.client.ProxyHttpClient;
import com.coc.data.constant.ClanTagConstants;
import com.coc.data.dto.*;
import com.coc.data.mapper.*;
import com.coc.data.model.*;
import com.coc.data.service.DataSyncService;
import com.coc.data.client.CocApiHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            log.info("clan tag {}, fetching current war info", clanTag);
            WarInfoDTO currentWarInfo = httpClient.getClanCurrentWarInfoByClanTag(clanTag);
            log.info("clan tag {}, fetch current war info done", clanTag);
            String currentWarTag = new SimpleDateFormat("yyyy-MM-dd").format(currentWarInfo.getPreparationStartTime());
            ClanWars currentWar = ClanWars.builder()
                .clanTag(clanTag)
                .tag(currentWarTag)
                .season(new SimpleDateFormat("yyyy-MM").format(currentWarInfo.getStartTime()))
                .state(currentWarInfo.getState())
                .teamSize(currentWarInfo.getTeamSize())
                .preparationStartTime(currentWarInfo.getPreparationStartTime())
                .startTime(currentWarInfo.getStartTime())
                .endTime(currentWarInfo.getEndTime())
                .isLeagueWar((byte) 0)
                .build();
            clanWarsMapper.insertOnDuplicateKeyUpdate(currentWar);

            List<ClanWarMembers> currentClanWarMemberDOList = new ArrayList<>();
            List<ClanWarMemberDTO> clanWarMemberDTOList = currentWarInfo.getClan().getMembers();
            List<ClanWarLogs> clanWarLogList = new ArrayList<>(100);
            log.info("clan tag {}, processing clan war member", clanTag);
            clanWarMemberDTOList.forEach(memberDTO -> {
                currentClanWarMemberDOList.add(ClanWarMembers.builder()
                    .clanTag(clanTag)
                    .memberTag(memberDTO.getTag())
                    .warTag(currentWarTag)
                    .attackTimeLeft(2L)
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
                                .warTag(currentWarTag)
                                .clanTag(clanTag)
                                .build()
                        );
                    });
                }
            });
            List<ClanWarMemberDTO> opponentClanMemberDTOList = currentWarInfo.getOpponent().getMembers();
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
                                .warTag(currentWarTag)
                                .clanTag(clanTag)
                                .build()
                        );
                    });
                }
            });
            if (!currentClanWarMemberDOList.isEmpty()) {
                clanWarMembersMapper.insertIgnoreExist(currentClanWarMemberDOList);
            }
            if(!clanWarLogList.isEmpty()) {
                clanWarLogsMapper.insertIgnoreExist(clanWarLogList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void calculateClanWarLogs() {
        List<ClanWarLogs> warLogList = clanWarLogsMapper.getUncalculatedLogs();
        if (!warLogList.isEmpty()) {
            log.info("calculating war logs...");
            for (ClanWarLogs warLog : warLogList) {
                addLogToClanWarMember(warLog);
            }
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
