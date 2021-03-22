package com.coc.data.service;

import com.coc.data.dto.ClanMemberDTO;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 12:30
 */
public interface PlayerService {
    
    /**
     * 刷新部落成员
     * @param clanMemberDTOList 
     * @return void
     * @author guokaiqiang
     * @date 2021/2/11 12:31
     **/
    void refreshClanMembers(List<ClanMemberDTO> clanMemberDTOList, String clanTag);
}
