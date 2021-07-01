package com.coc.data.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/8/1 16:57
 */
@Data
public class ClanWarInfoDTO {

    private String tag;

    private String name;

    private IconUrlDTO badgeUrls;

    private Long clanLevel;

    private Long attacks;

    private Long stars;

    private BigDecimal destructionPercentage;

    private List<ClanWarMemberDTO> members;
}
