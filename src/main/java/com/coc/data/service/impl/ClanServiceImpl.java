package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.constant.ClanTagConstants;
import com.coc.data.dto.*;
import com.coc.data.mapper.ClanMapper;
import com.coc.data.mapper.ClanWarMapper;
import com.coc.data.model.base.Clan;
import com.coc.data.model.base.ClanWar;
import com.coc.data.service.*;
import com.coc.data.util.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 11:30
 */
@Service
@Slf4j
public class ClanServiceImpl implements ClanService {

    /**
     * others
     **/
    @Resource
    private CocApiHttpClient httpClient;

    /**
     * mapper
     **/
    @Resource
    private ClanMapper clanMapper;
    @Resource
    private ClanWarMapper clanWarMapper;

    /**
     * service
     **/
    @Resource
    private PlayerService playerService;
    @Resource
    private ClanWarService clanWarService;
    @Resource
    private MiniProgramMessageService miniProgramMessageService;
    @Resource
    private UserService userService;

    @Override
    public ClanInfoDTO getClanInfo(String clanTag) {
        return httpClient.getClanInfoByTag(clanTag);
    }

    @Override
    public void syncClanBaseInfo(String clanTag) {
        ClanInfoDTO clanInfo = getClanInfo(clanTag);
        Clan clan = Clan.builder()
            .name(clanInfo.getName())
            .tag(clanTag)
            .build();
        clanMapper.insertOnDuplicateKeyUpdate(clan);
        playerService.refreshClanMembers(clanInfo.getMemberList(), clanTag);
    }

    @Override
    public void syncLeagueGroupInfo(String clanTag) {
        Clan clan = clanMapper.selectByClanTag(clanTag);
        log.info("开始获取部落 {} 的联赛信息",clan.getName());
        LeagueGroupInfoDTO leagueGroupInfo;
        try {
            leagueGroupInfo = httpClient.getClanLeagueGroupInfoByClanTag(clan.getTag());
        } catch (Exception e) {
            log.info("{} 部落没有进行中的联赛", clan.getName());
            log.error(e.getMessage(),e);
            return;
        }
        log.info("获取部落 {} 的联赛信息完毕",clan.getName());
        List<LeagueGroupRoundDTO> rounds = leagueGroupInfo.getRounds();
        String leagueTag = getLeagueTag(leagueGroupInfo);
//        for (LeagueGroupRoundDTO round : rounds) {
        for (int i=0; i< rounds.size(); i++) {
            LeagueGroupRoundDTO round = rounds.get(i);
            List<String> warTags = round.getWarTags();
            if (ClanTagConstants.DEFAULT_LEAGUE_GROUP_WAR_TAG.equals(warTags.get(0))) {
                continue;
            }
            for (String warTag : warTags) {
                List<ClanWar> warList =
                    clanWarMapper.getWarsByWarTagList(Collections.singletonList(warTag));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (!ObjectUtils.isEmpty(warList)) {
                    ClanWar war = warList.get(0);
                    log.info(
                        "战争 {} 已经被同步，开始时间{}， 结束时间{},状态：{}",
                        war.getTag(),
                        dateFormat.format(war.getStartTime()),
                        dateFormat.format(war.getEndTime()),
                        war.getState()
                    );
                    continue;
                }
                WarInfoDTO warInfo = httpClient.getClanLeagueGroupWarInfoByTag(warTag);;
                warInfo.setTag(warTag);
                if (ObjectUtils.isEmpty(warInfo)) {
                    continue;
                }
                warInfo.setSeason(leagueGroupInfo.getSeason());
                // 先记录下对战信息
                clanWarService.recLeagueWarInfo(warInfo, warInfo.getClan().getTag(), leagueTag);
                // 记录下对战详细信息
                clanWarService.recWarMemberAndWarLogs(warInfo, warInfo.getClan().getTag());
                if (i == 0) {
                    miniProgramMessageService.sendClanLeagueStartMessage(warInfo);
                }
            }
        }
    }

    String getLeagueTag(LeagueGroupInfoDTO leagueGroupInfoDTO) {
        return FormatUtil.formatLeagueTag(leagueGroupInfoDTO);
    }

    @Override
    public boolean atLeagueWar(String tag) {
        try {
            LeagueGroupInfoDTO leagueGroupInfo =
                httpClient.getClanLeagueGroupInfoByClanTag(tag);
            if (!ObjectUtils.isEmpty(leagueGroupInfo)) {
                return true;
            }
        } catch (Exception e) {
            log.info("{} 部落没有进行中的联赛", tag);
            log.error(e.getMessage(),e);
        }
        return false;
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
}
