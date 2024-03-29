package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Clans {
    private Integer id;

    private String tag;

    private String name;

    private Long provideClanWarReport;

    private Long provideLeagueGroupWarReport;

    private Date createTime;

    private Date updateTime;
}