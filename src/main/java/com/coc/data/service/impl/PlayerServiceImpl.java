package com.coc.data.service.impl;

import com.coc.data.controller.vo.user.PlayerBriefVO;
import com.coc.data.dto.ClanMemberDTO;
import com.coc.data.enums.UserClanStateEnum;
import com.coc.data.mapper.ClanMapper;
import com.coc.data.mapper.ClanMemberMapper;
import com.coc.data.mapper.PlayerMapper;
import com.coc.data.mapper.UserClanRelationMapper;
import com.coc.data.model.base.Clan;
import com.coc.data.model.base.ClanMember;
import com.coc.data.model.base.Player;
import com.coc.data.model.base.UserClanRelation;
import com.coc.data.service.PlayerService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private ClanMapper clanMapper;
    @Resource
    private UserClanRelationMapper userClanRelationMapper;

    @Override
    public void refreshClanMembers(List<ClanMemberDTO> clanMemberDTOList, String clanTag) {
        if (ObjectUtils.isEmpty(clanMemberDTOList)) {
            return;
        }
        List<ClanMember> clanMembersList = Lists.newLinkedList();
        List<Player> playerList = Lists.newLinkedList();
        Clan clan = clanMapper.selectByClanTag(clanTag);
        List<UserClanRelation> userClanRelations = Lists.newLinkedList();
        clanMemberDTOList.forEach(clanMemberDTO -> {
            clanMembersList.add(ClanMember.builder()
                .clanTag(clanTag)
                .tag(clanMemberDTO.getTag())
                .name(clanMemberDTO.getName())
                .build()
            );
            playerList.add(Player.builder()
                .name(clanMemberDTO.getName())
                .tag(clanMemberDTO.getTag())
                .build());
        });
        if (clanMembersList.size() > 0) {
            clanMemberMapper.batchDelete(clanMembersList);
            clanMemberMapper.batchInsert(clanMembersList);
        }
        if (playerList.size() > 0) {
            playerMapper.batchDelete(playerList);
            playerMapper.batchInsert(playerList);
            List<Long> tempUserIdList =
                playerMapper.selectUserIdListByPlayerTagList(playerList.stream().map(Player::getTag).collect(Collectors.toList()));
            tempUserIdList.forEach(userId -> {
                userClanRelations.add(UserClanRelation.builder()
                    .clanId(clan.getId().longValue())
                    .userId(userId)
                    .state(UserClanStateEnum.OK.code)
                    .build());
            });
            if (userClanRelations.size() > 0) {
                userClanRelationMapper.batchInsert(userClanRelations);
            }
        }
    }
}
