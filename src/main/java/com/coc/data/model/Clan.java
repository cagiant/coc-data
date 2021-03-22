package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Clan {
    private Integer id;

    private String tag;

    private String name;

    private Byte provideClanWarReport;

    private Byte provideLeagueWarReport;

    private Date expireTime;

    private Date createTime;

    private Date updateTime;
}