package com.coc.data.controller.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/27 09:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramBindPlayerRequest {

	private String openId;

	private String playerTag;
}
