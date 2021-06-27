package com.coc.data.controller.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/27 14:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerBriefVO {

	private String openId;

	private String tag;

	private String name;
}
