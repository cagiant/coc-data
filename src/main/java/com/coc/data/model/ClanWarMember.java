package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClanWarMember {
    private Long id;

    private Long warId;

    private String memberTag;

    private String memberName;

    private String season;

    private String warTag;

    private String clanTag;

    private Byte isDeleted;

    private Date createTime;

    private Date updateTime;
}