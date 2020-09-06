package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReportClanWarMember {
    private Long id;

    private String memberTag;

    private String season;

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