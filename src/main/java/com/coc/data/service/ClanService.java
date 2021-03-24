package com.coc.data.service;

import com.coc.data.dto.ClanInfoDTO;

/**
 * @author guokaiqiang
 * @date 2021/2/11 11:28
 */
public interface ClanService {

    /**
     * 从接口获取部落信息
     * @param clanTag
     * @return com.coc.data.dto.ClanInfoDTO
     * @author guokaiqiang
     * @date 2021/2/11 11:29
     **/
    ClanInfoDTO getClanInfo(String clanTag);

    /**
     * 同步部落基础信息
     * @param clanTag
     * @return void
     * @author guokaiqiang
     * @date 2021/2/11 11:32
     **/
    void syncClanBaseInfo(String clanTag);

    /**
     * 同步联赛组信息
     * @param clanTag
     * @return void
     * @author guokaiqiang
     * @date 2021/3/7 17:06
     **/
    void syncLeagueGroupInfo(String clanTag);

    /**
     * 检查一个部落是否处于联赛期间
     * @param tag
     * @return boolean
     * @author guokaiqiang
     * @date 2021/3/24 22:20
     **/
    boolean atLeagueWar(String tag);
}
