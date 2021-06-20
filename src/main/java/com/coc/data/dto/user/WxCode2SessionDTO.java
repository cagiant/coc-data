package com.coc.data.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/14 21:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxCode2SessionDTO {

	@JsonProperty("session_key")
	private String sessionKey;

	@JsonProperty("unionid")
	private String unionId;

	@JsonProperty("openid")
	private String openId;
}
