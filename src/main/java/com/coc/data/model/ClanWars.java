package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClanWars {
    private Long id;

    private String clanTag;

    private String tag;

    private String season;

    private String state;

    private Long teamSize;

    private Date preparationStartTime;

    private Date startTime;

    private Date endTime;

    private Byte isLeagueWar;

    private Date createTime;

    private Date updateTime;
}