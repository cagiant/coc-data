package com.coc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2020/8/1 17:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttackDTO {

    private String attackerTag;

    private String defenderTag;

    private Long stars;

    private String destructionPercentage;

    private Long order;
}
