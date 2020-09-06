package com.coc.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanWarMembers {
    private Long id;

    private String memberTag;

    private String memberName;

    private String warTag;

    private String clanTag;

    private Long attackThreeStarTime;

    private Long attackTwoStarTime;

    private Long attackOneStarTime;

    private Long attackNoStarTime;

    private Long attackTimeUsed;

    private Long attackTimeLeft;

    private Long defenseThreeStarTime;

    private Boolean leagueWar;

    private Date createTime;

    private Date updateTime;
}