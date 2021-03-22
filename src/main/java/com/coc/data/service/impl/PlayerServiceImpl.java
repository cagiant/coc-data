package com.coc.data.service.impl;

import com.coc.data.dto.ClanMemberDTO;
import com.coc.data.mapper.ClanMemberMapper;
import com.coc.data.mapper.PlayerMapper;
import com.coc.data.model.ClanMember;
import com.coc.data.model.ClanMembers;
import com.coc.data.model.Player;
import com.coc.data.model.Players;
import com.coc.data.service.PlayerService;
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
        List<ClanMember> clanMembersList = new ArrayList<>();
        List<Player> playersList = new ArrayList<>();
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
