package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClanWarLog {
    private Long id;

    private Long warId;

    private String attackerTag;

    private String defenderTag;

    private String warTag;

    private String clanTag;

    private Short star;

    private String destructionPercentage;

    private Integer attackOrder;

    private Date createTime;
}