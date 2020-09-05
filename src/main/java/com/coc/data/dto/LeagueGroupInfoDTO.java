package com.coc.data.dto;

import lombok.Data;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/9/5 22:04
 */
@Data
public class LeagueGroupInfoDTO {

    private String state;

    private String season;

    private List<LeagueGroupClanInfoDTO> clans;

    private List<LeagueGroupRoundDTO> rounds;
}
