package com.coc.data.service.impl;

import com.coc.data.dto.ClanMemberDTO;
import com.coc.data.mapper.ClanMemberMapper;
import com.coc.data.mapper.PlayerMapper;
import com.coc.data.model.base.ClanMember;
import com.coc.data.model.base.Player;
import com.coc.data.service.PlayerService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 12:31
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Resource
    private ClanMemberMapper clanMemberMapper;
    @Resource
    private PlayerMapper playerMapper;

    @Override
    public void refreshClanMembers(List<ClanMemberDTO> clanMemberDTOList, String clanTag) {
        if (ObjectUtils.isEmpty(clanMemberDTOList)) {
            return;
        }
        List<ClanMember> clanMembersList = Lists.newLinkedList();
        List<Player> playersList = Lists.newLinkedList();
        clanMemberDTOList.forEach(clanMemberDTO -> {
            clanMembersList.add(ClanMember.builder()
                .clanTag(clanTag)
                .tag(clanMemberDTO.getTag())
                .name(clanMemberDTO.getName())
                .build()
            );
            playersList.add(Player.builder()
                .name(clanMemberDTO.getName())
                .tag(clanMemberDTO.getTag())
                .build());
        });
        clanMemberMapper.batchInsert(clanMembersList);
        playerMapper.batchInsert(playersList);
    }
}
