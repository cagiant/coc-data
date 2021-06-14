package com.coc.data.controller.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/14 21:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxUserInfoVO {

	private String nickName;

	private Integer gender;

	private String language;

	private String country;

	private String province;

	private String city;

	private String avatarUrl;
}
