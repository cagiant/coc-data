package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Player {
    private Long id;

    private String tag;

    private String name;

    private Date createTime;

    private Date updateTime;
}