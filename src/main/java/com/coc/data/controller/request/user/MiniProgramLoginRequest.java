package com.coc.data.controller.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2021/6/14 21:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramLoginRequest {

	private String code;
}
