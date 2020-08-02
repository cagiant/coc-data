package com.coc.data.dto;

import lombok.Data;

/**
 * @author guokaiqiang
 * @date 2020/8/1 17:06
 */
@Data
public class AttackDTO {

    private String attackerTag;

    private String defenderTag;

    private Long stars;

    private String destructionPercentage;

    private Long order;
}
