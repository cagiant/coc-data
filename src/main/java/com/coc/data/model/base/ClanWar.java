package com.coc.data.model.base;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClanWar {
    private Long id;

    private String clanTag;

    private String tag;

    private String season;

    private String state;

    private Short teamSize;

    private Date preparationStartTime;

    private Date startTime;

    private Date endTime;

    private String type;

    private Date createTime;

    private Date updateTime;
}