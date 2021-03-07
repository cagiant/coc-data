package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.constant.ClanTagConstants;
import com.coc.data.dto.ClanInfoDTO;
import com.coc.data.dto.LeagueGroupInfoDTO;
import com.coc.data.dto.LeagueGroupRoundDTO;
import com.coc.data.dto.WarInfoDTO;
import com.coc.data.mapper.ClanMapper;
import com.coc.data.mapper.ClanWarMapper;
import com.coc.data.model.Clan;
import com.coc.data.model.ClanWar;
import com.coc.data.model.ClanWars;
import com.coc.data.service.ClanService;
import com.coc.data.service.ClanWarService;
import com.coc.data.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 11:30
 */
@Service
@Slf4j
public class ClanServiceImpl implements ClanService {

    @Resource
    private CocApiHttpClient httpClient;

    @Resource
    private ClanMapper clanMapper;
    @Resource
    private ClanWarMapper clanWarMapper;

    @Resource
    private PlayerService playerService;
    @Resource
    private ClanWarService clanWarService;

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
        for (LeagueGroupRoundDTO round : rounds) {
            List<String> warTags = round.getWarTags();
            if (ClanTagConstants.DEFAULT_LEAGUE_GROUP_WAR_TAG.equals(warTags.get(0))) {
                continue;
            }
            List<ClanWar> warList = clanWarMapper.getWarsByWarTagList(warTags);
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
            WarInfoDTO warInfo = getLeagueGroupWarBelongsToClan(warTags, clan.getTag());
            warInfo.setSeason(leagueGroupInfo.getSeason());
            if (ObjectUtils.isEmpty(warInfo)) {
                continue;
            }
            // 先记录下对战信息
            clanWarService.recLeagueWarInfo(warInfo,  clan.getTag());
            // 先记录下对战详细信息
            clanWarService.recWarMemberAndWarLogs(warInfo, clan.getTag());
        }
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
