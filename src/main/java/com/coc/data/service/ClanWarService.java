package com.coc.data.service;

import com.coc.data.dto.WarInfoDTO;

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
    void syncClanLeagueWarInfo();

    void recNormalWarInfo(WarInfoDTO currentWarInfo, String clanTag);

    void recLeagueWarInfo(WarInfoDTO currentWarInfo, String clanTag);

    void recWarMemberAndWarLogs(WarInfoDTO warInfo, String clanTag);
}
