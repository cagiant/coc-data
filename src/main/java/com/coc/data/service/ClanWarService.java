package com.coc.data.service;

import com.coc.data.dto.WarInfoDTO;
import com.coc.data.model.base.Clan;
import com.coc.data.model.base.ClanWar;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 12:45
 */
public interface ClanWarService {

    /**
     * 同步日常部落战
     * @param clanTag
     * @return void
     * @author guokaiqiang
     * @date 2021/2/11 13:15
     **/
    void syncClanNormalWarInfo(String clanTag);

    /**
     * 刷新联赛战争信息
     * @param
     * @return void
     * @author guokaiqiang
     * @date 2021/3/7 17:21
     **/
    void syncClanLeagueWarInfo(String warTag, String clanTag);

    void syncClanLeagueWarInfos(List<ClanWar> clanWarList, String clanTag);

    void recNormalWarInfo(WarInfoDTO currentWarInfo, String clanTag);

    void recLeagueWarInfo(WarInfoDTO currentWarInfo, String clanTag, String leagueTag);

    void recWarMemberAndWarLogs(WarInfoDTO warInfo, String clanTag);

    /**
     * 同步部落当前进行中的战争信息
     * @param clan
     * @return void
     * @author guokaiqiang
     * @date 2021/3/28 21:11
     **/
    void syncClanCurrentWarInfo(Clan clan);
}
