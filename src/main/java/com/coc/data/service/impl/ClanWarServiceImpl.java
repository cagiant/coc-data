package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.constant.ClanWarConstants;
import com.coc.data.dto.*;
import com.coc.data.enums.ClanWarTypeEnum;
import com.coc.data.mapper.ClanMapper;
import com.coc.data.mapper.ClanWarLogMapper;
import com.coc.data.mapper.ClanWarMapper;
import com.coc.data.mapper.ClanWarMemberMapper;
import com.coc.data.model.base.Clan;
import com.coc.data.model.base.ClanWar;
import com.coc.data.model.base.ClanWarLog;
import com.coc.data.model.base.ClanWarMember;
import com.coc.data.service.ClanWarService;
import com.coc.data.util.DateUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
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

    @Resource
    private ClanWarMapper clanWarMapper;
    @Resource
    private ClanWarMemberMapper clanWarMemberMapper;
    @Resource
    private ClanWarLogMapper clanWarLogMapper;
    @Resource
    private ClanMapper clanMapper;

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
            if (ClanWarConstants.NOT_IN_WAR.equals(currentWarInfo.getState())) {
                return;
            }
            if (ObjectUtils.isEmpty(currentWarInfo)) {
                log.error("部落标签{}, 获取当前对战信息失败。", clanTag);
                return;
            }
            currentWarInfo.setTag(getNormalWarTag(currentWarInfo.getPreparationStartTime()));
            currentWarInfo.setSeason(getWarSeason(currentWarInfo.getStartTime()));
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
        recLeagueWarInfo(warInfo, clanTag);
        // 记录下对战详细信息
        log.info("更新对战详细信息");
        if (ClanWarConstants.WAR_ENDED.equals(warInfo.getState())) {
            log.info("战争已结束，更新参战人员");
            refreshLeagueGroupWarMembers(warInfo, clanTag);
        }
        recWarMemberAndWarLogs(warInfo, clanTag);
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
        if (clanTag.equals(warInfo.getClan().getTag())) {
            clanWarInfo = warInfo.getClan();
        } else {
            clanWarInfo = warInfo.getOpponent();
        }

        List<ClanWarMemberDTO> clanWarMemberList = clanWarInfo.getMembers();
        List<String> clanWarMemberTagList = clanWarMemberList.stream().map(ClanWarMemberDTO::getTag).collect(Collectors.toList());
        clanWarMemberMapper.deleteNotInClanWarMember(warInfo.getTag(), clanTag, clanWarMemberTagList);
    }

    String getWarSeason(Date startTime) {
        LocalDate startTimeDate = DateUtil.asLocalDate(startTime);
        return String.format("%s-%s", startTimeDate.getYear(),
            startTimeDate.getMonthValue() > 9 ? startTimeDate.getMonthValue() :
                "0" + startTimeDate.getMonthValue());
    }

    String getNormalWarTag(Date preparationStartTime) {
        return DateUtil.asLocalDate(preparationStartTime).toString();
    }

    @Override
    public void recNormalWarInfo(WarInfoDTO currentWarInfo, String clanTag) {
        recWarInfo(currentWarInfo, clanTag, ClanWarTypeEnum.NORMAL.code);
    }

    @Override
    public void recLeagueWarInfo(WarInfoDTO currentWarInfo, String clanTag) {
        recWarInfo(currentWarInfo, clanTag, ClanWarTypeEnum.LEAGUE.code);
    }

    private void recWarInfo(WarInfoDTO warInfo, String clanTag, String type) {
        ClanWar currentWar = ClanWar.builder()
            .clanTag(clanTag)
            .tag(warInfo.getTag())
            .season(warInfo.getSeason())
            .state(warInfo.getState())
            .teamSize(warInfo.getTeamSize().shortValue())
            .preparationStartTime(warInfo.getPreparationStartTime())
            .startTime(warInfo.getStartTime())
            .endTime(warInfo.getEndTime())
            .type(type)
            .build();

        clanWarMapper.insertOnDuplicateKeyUpdate(currentWar);
    }

    @Override
    public void recWarMemberAndWarLogs(WarInfoDTO warInfo, String clanTag) {
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
        String season = getWarSeason(warInfo.getStartTime());
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
            buildClanWarLog(warInfo, clanTag, clanWarLogList, memberDTO);
        });
        List<ClanWarMemberDTO> opponentClanMemberDTOList = opponentWarInfo.getMembers();
        opponentClanMemberDTOList.forEach(memberDTO -> {
            buildClanWarLog(warInfo, clanTag, clanWarLogList, memberDTO);
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

    @Override
    public void syncClanCurrentWarInfo(Clan clan) {
        ClanWar clanWar = clanWarMapper.getStartedWarByClanTag(clan.getTag());
        if (!ObjectUtils.isEmpty(clanWar) &&
            ClanWarTypeEnum.LEAGUE.code.equals(clanWar.getType())
        ) {
            syncClanLeagueWarInfo(clanWar.getTag(), clan.getTag());
        } else {
            syncClanNormalWarInfo(clan.getTag());
        }
    }

    private void buildClanWarLog(WarInfoDTO warInfo, String clanTag, List<ClanWarLog> clanWarLogList, ClanWarMemberDTO memberDTO) {
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
    }
}
