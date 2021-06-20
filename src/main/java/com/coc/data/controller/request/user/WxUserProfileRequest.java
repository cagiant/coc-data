package com.coc.data.controller.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/20 10:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxUserProfileRequest {

	private String code;

	private String nickName;

	private Integer gender;

	private String language;

	private String country;

	private String province;

	private String city;

	private String avatarUrl;
}
