package com.coc.data.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/3/25 22:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDataRequest {

	private String tag;

	private String clanTag;

	private String season;

	private Boolean league;
}
