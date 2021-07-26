package com.coc.data.controller.request.war;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author guokaiqiang
 * @date 2021/7/25 21:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarDetailGetRequest {

	@NotEmpty(message = "战争标签不能为空")
	private String warTag;

	@NotEmpty(message = "部落标签不能为空")
	private String clanTag;
}
