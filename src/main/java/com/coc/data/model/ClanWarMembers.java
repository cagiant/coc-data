package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClanWarMembers {
    private Long id;

    private String memberTag;

    private String warTag;

    private String clanTag;

    private Long attackThreeStarTime;

    private Long attackTwoStarTime;

    private Long attackOneStarTime;

    private Long attackNoStarTime;

    private Long attackTimeUsed;

    private Long attackTimeLeft;

    private Long defenseThreeStarTime;

    private Date createTime;

    private Date updateTime;
}