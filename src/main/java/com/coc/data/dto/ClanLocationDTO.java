package com.coc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author guokaiqiang
 * @date 2020/8/1 11:39
 */
@Data
public class ClanLocationDTO {

    private Long id;

    private String name;

    private Boolean isCountry;

    private String countryCode;
}
