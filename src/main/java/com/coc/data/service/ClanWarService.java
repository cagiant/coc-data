package com.coc.data.service;

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
    void syncClanCurrentWarInfo(String clanTag);
}
