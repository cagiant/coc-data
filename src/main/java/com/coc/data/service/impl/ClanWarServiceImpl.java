package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.constant.ClanWarConstants;
import com.coc.data.dto.ClanWarInfoDTO;
import com.coc.data.dto.ClanWarMemberDTO;
import com.coc.data.dto.WarInfoDTO;
import com.coc.data.mapper.ClanWarLogMapper;
import com.coc.data.mapper.ClanWarMapper;
import com.coc.data.mapper.ClanWarMemberMapper;
import com.coc.data.model.*;
import com.coc.data.service.ClanWarService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 12:46
 */
@Service
@Slf4j
public class ClanWarServiceImpl implements ClanWarService {

    @Resource
    private ClanWarMapper clanWarMapper;
    @Resource
    private ClanWarMemberMapper clanWarMemberMapper;
    @Resource
    private ClanWarLogMapper clanWarLogMapper;
    @Resource
    private CocApiHttpClient httpClient;

    @Override
    public void syncClanCurrentWarInfo(String clanTag) {
        int count = clanWarMapper.getUnStartedClanWar(new Date(), clanTag);
        if (count > 0) {
            return;
        }
        WarInfoDTO currentWarInfo = httpClient.getClanCurrentWarInfoByClanTag(clanTag);
        if (ClanWarConstants.NOT_IN_WAR.equals(currentWarInfo.getState())) {
            return;
        }
        if (ObjectUtils.isEmpty(currentWarInfo)) {
            log.error("部落标签{}, 获取当前对战信息失败。", clanTag);
            return;
        }
        String currentWarTag = new SimpleDateFormat("yyyy-MM-dd").format(currentWarInfo.getPreparationStartTime());
        currentWarInfo.setTag(currentWarTag);
        currentWarInfo.setSeason(new SimpleDateFormat("yyyy-MM").format(currentWarInfo.getStartTime()));
        recCurrentWarInfo(currentWarInfo, clanTag);
        recWarMemberAndWarLogs(currentWarInfo, clanTag);
    }

    private void recCurrentWarInfo(WarInfoDTO currentWarInfo, String clanTag) {
        recWarInfo(currentWarInfo, clanTag, false);
    }

    private void recLeagueWarInfo(WarInfoDTO currentWarInfo, String clanTag) {
        recWarInfo(currentWarInfo, clanTag, true);
    }

    private void recWarInfo(WarInfoDTO warInfo, String clanTag, boolean isLeagueWar) {
        ClanWars currentWar = ClanWars.builder()
            .clanTag(clanTag)
            .tag(warInfo.getTag())
            .season(warInfo.getSeason())
            .state(warInfo.getState())
            .teamSize(warInfo.getTeamSize())
            .preparationStartTime(warInfo.getPreparationStartTime())
            .startTime(warInfo.getStartTime())
            .endTime(warInfo.getEndTime())
            .isLeagueWar(isLeagueWar ? (byte)1 : (byte)0)
            .build();

        clanWarMapper.insertOnDuplicateKeyUpdate(currentWar);
    }

    private void recWarMemberAndWarLogs(WarInfoDTO warInfo, String clanTag) {
        List<ClanWarMember> currentClanWarMemberDOList = Lists.newLinkedList();
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
        List<ClanWarLog> clanWarLogList = Lists.newLinkedList();
        log.info("部落 {}, 正在处理参战成员及对战记录信息，战争标签：{}", clanWarInfo.getName(), warInfo.getTag());
        clanWarMemberDTOList.forEach(memberDTO -> {
            currentClanWarMemberDOList.add(ClanWarMember.builder()
                .clanTag(clanTag)
                .memberTag(memberDTO.getTag())
                .memberName(memberDTO.getName())
                .season(season)
                .warTag(warInfo.getTag())
                .build()
            );
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
                        ClanWarLog.builder()
                            .attackerTag(attack.getAttackerTag())
                            .defenderTag(attack.getDefenderTag())
                            .destructionPercentage(attack.getDestructionPercentage())
                            .attackOrder(attack.getOrder().intValue())
                            .star(attack.getStars().shortValue())
                            .warTag(warInfo.getTag())
                            .clanTag(clanTag)
                            .build()
                    );
                });
            }
        });
        if (!currentClanWarMemberDOList.isEmpty()) {
            log.info("部落 {}, 正在保存参战成员信息", clanWarInfo.getName());
            clanWarMemberMapper.batchInsert(currentClanWarMemberDOList);
        }
        if(!clanWarLogList.isEmpty()) {
            log.info("部落 {}, 正在保存对战记录信息", clanWarInfo.getName());
            clanWarLogMapper.batchInsert(clanWarLogList);
        }
    }
}
