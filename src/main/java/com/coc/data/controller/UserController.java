package com.coc.data.controller;

import com.coc.data.controller.request.user.MiniProgramLoginRequest;
import com.coc.data.controller.vo.user.WxUserInfoVO;
import com.coc.data.dto.user.WxUserInfoDTO;
import com.coc.data.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author guokaiqiang
 * @date 2021/6/14 21:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	UserService userService;


	@PostMapping("/miniProgramLogin")
	public WxUserInfoVO miniProgramLogin(MiniProgramLoginRequest loginRequest) {
		WxUserInfoDTO dto = userService.miniProgramLogin(loginRequest.getCode());

		return WxUserInfoVO.builder()
			.avatarUrl(dto.getAvatarUrl())
			.nickName(dto.getNickName())
			.build();
	}

}
