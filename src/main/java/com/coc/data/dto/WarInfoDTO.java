package com.coc.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author guokaiqiang
 * @date 2020/8/1 16:55
 */
@Data
public class WarInfoDTO {

    private String state;

    private Long teamSize;

    private String tag;

    private String season;

//    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSS'Z'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date preparationStartTime;

//    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSS'Z'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;

//    @JsonFormat(pattern = "yyyyMMdd'T'HHmmss.SSS'Z'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    private ClanWarInfoDTO clan;

    private ClanWarInfoDTO opponent;
}
