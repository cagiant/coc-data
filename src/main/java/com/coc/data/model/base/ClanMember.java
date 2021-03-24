package com.coc.data.model.base;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClanMember {
    private Integer id;

    private String clanTag;

    private String tag;

    private String name;

    private Boolean isDeleted;

    private Date createTime;

    private Date updateTime;
}