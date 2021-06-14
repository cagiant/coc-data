package com.coc.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/6 22:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxWaterMarkDTO {

	private Long timestamp;

	private String appid;
}
