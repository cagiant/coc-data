package com.coc.data.dto.user;

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

	private String sessionKey;

	private String unionId;

	private String openId;
}
