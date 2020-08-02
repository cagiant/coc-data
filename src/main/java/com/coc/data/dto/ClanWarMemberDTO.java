package com.coc.data.dto;

import lombok.Data;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/8/1 17:03
 */
@Data
public class ClanWarMemberDTO {

    private String tag;

    private String name;

    private Long townhallLevel;

    private Long mapPosition;

    private List<AttackDTO> attacks;

    private Long opponentAttacks;

    private AttackDTO bestOpponentAttack;
}
