package com.coc.data.dto;

import lombok.Data;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/9/5 22:06
 */
@Data
public class LeagueGroupClanInfoDTO {

    private String tag;

    private String name;

    private Long clanLevel;

    private IconUrlDTO badgeUrls;

    private List<LeagueGroupClanMemberDTO> members;
}
