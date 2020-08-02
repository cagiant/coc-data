package com.coc.data.service;

import com.coc.data.constant.ClanTagConstants;
import com.coc.data.dto.*;
import com.coc.data.mapper.*;
import com.coc.data.model.*;
import com.coc.data.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/7/30 22:02
 */
@Service("dataSyncService")
@Slf4j
public class DataSyncServiceImpl implements DataSyncService {

    @Resource
    private HttpUtil httpUtil;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void syncClanInfo() {
        List<String> clanTags = Arrays.asList(ClanTagConstants.WHITE_LIST_TAGS.split(","));
        for (String clanTag : clanTags) {
            ClanInfoDTO clanInfo = httpUtil.getClanInfoByTag(clanTag);
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

    @Override
    public void syncChanCurrentWarInfo() {
        List<String> clanTags = Arrays.asList(ClanTagConstants.WHITE_LIST_TAGS.split(","));
        for (String clanTag : clanTags) {
            WarInfoDTO currentWarInfo = httpUtil.getClanCurrentWarInfoByClanTag(clanTag);
            String currentWarTag = new SimpleDateFormat("yyyy-MM-dd").format(currentWarInfo.getStartTime());
            ClanWars currentWar = ClanWars.builder()
                .tag(currentWarTag)
                .season(new SimpleDateFormat("yyyy-MM").format(currentWarInfo.getStartTime()))
                .state(currentWarInfo.getState())
                .teamSize(currentWarInfo.getTeamSize())
                .preparationStartTime(currentWarInfo.getPreparationStartTime())
                .startTime(currentWarInfo.getStartTime())
                .isLeagueWar((byte) 0)
                .build();
            clanWarsMapper.insertOnDuplicateKeyUpdate(currentWar);

            List<ClanWarMemberDTO> clanWarMemberDTOList = currentWarInfo.getClan().getMembers();
            clanWarMemberDTOList.addAll(currentWarInfo.getOpponent().getMembers());
            List<ClanWarMembers> currentClanWarMemberDOList = new ArrayList<>();
            List<ClanWarLogs> clanWarLogList = new ArrayList<>(100);
            clanWarMemberDTOList.forEach(memberDTO -> {
                if (memberDTO.getTag().equals(clanTag)) {
                    currentClanWarMemberDOList.add(ClanWarMembers.builder()
                        .clanTag(clanTag)
                        .memberTag(memberDTO.getTag())
                        .warTag(currentWarTag)
                        .attackTimeLeft(2L)
                        .build()
                    );
                }
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
                                .build()
                        );
                    });
                }
            });
            clanWarMembersMapper.insertIgnoreExist(currentClanWarMemberDOList);
            if(!clanWarLogList.isEmpty()) {
                clanWarLogsMapper.insertIgnoreExist(clanWarLogList);
            }
        }
    }
}
