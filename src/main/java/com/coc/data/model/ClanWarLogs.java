package com.coc.data.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClanWarLogs {
    private Long id;

    private String attackerTag;

    private String defenderTag;

    private String warTag;

    private Long star;

    private String destructionPercentage;

    private Long attackOrder;

    private Boolean hasCalculated;

    private Date createTime;
}