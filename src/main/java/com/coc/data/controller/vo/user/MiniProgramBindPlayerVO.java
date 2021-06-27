package com.coc.data.controller.vo.user;

import lombok.Builder;
import lombok.Data;

/**
 * @author guokaiqiang
 * @date 2021/6/27 09:07
 */
@Data
@Builder
public class MiniProgramBindPlayerVO {

	private Boolean bindSuccess;

	private String msg;
}
