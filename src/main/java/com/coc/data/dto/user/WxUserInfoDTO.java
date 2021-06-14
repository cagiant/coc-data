package com.coc.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/6 18:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxUserInfoDTO {

	private String nickName;

	private Integer gender;

	private String language;

	private String country;

	private String province;

	private String city;

	private String avatarUrl;

	private String unionId;

	private String openId;

	private WxWaterMarkDTO watermark;
}
