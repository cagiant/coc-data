package com.coc.data.controller;

import com.coc.data.controller.request.user.MiniProgramBindPlayerRequest;
import com.coc.data.controller.request.user.MiniProgramLoginRequest;
import com.coc.data.controller.request.user.UserSettingRequest;
import com.coc.data.controller.request.user.WxUserProfileRequest;
import com.coc.data.controller.vo.user.MiniProgramBindPlayerVO;
import com.coc.data.controller.vo.user.PlayerBriefVO;
import com.coc.data.controller.vo.user.WxUserInfoVO;
import com.coc.data.dto.PlayerDTO;
import com.coc.data.dto.user.UserSettingDTO;
import com.coc.data.dto.user.WxUserInfoDTO;
import com.coc.data.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

		if (dto == null) {
			return null;
		}

		return WxUserInfoVO.builder()
			.avatarUrl(dto.getAvatarUrl())
			.nickName(dto.getNickName())
			.openId(dto.getOpenId())
			.unionId(dto.getUnionId())
			.build();
	}

	@PostMapping("/refreshMiniProgramUserInfo")
	public WxUserInfoVO refreshMiniProgramUserInfo(WxUserProfileRequest userProfileRequest) {
		WxUserInfoDTO dto = userService.refreshUser(userProfileRequest);

		return WxUserInfoVO.builder()
			.avatarUrl(dto.getAvatarUrl())
			.nickName(dto.getNickName())
			.openId(dto.getOpenId())
			.unionId(dto.getUnionId())
			.build();
	}

	@GetMapping("/getPlayerInfo")
	public PlayerDTO getPlayerInfo(@RequestParam("tag") String tag) {

		return userService.getPlayerInfo(tag);
	}

	@PostMapping("/bindPlayer")
	public MiniProgramBindPlayerVO bindPlayer(MiniProgramBindPlayerRequest bindPlayerRequest) {
		String openId = bindPlayerRequest.getOpenId();
		String playerTag = bindPlayerRequest.getPlayerTag();

		return userService.bindPlayer(openId, playerTag);
	}

	@PostMapping("/unbindPlayer")
	public MiniProgramBindPlayerVO unbindPlayer(MiniProgramBindPlayerRequest bindPlayerRequest) {
		String openId = bindPlayerRequest.getOpenId();
		String playerTag = bindPlayerRequest.getPlayerTag();

		return userService.unbindPlayer(openId, playerTag);
	}

	@PostMapping("saveUserSetting")
	public UserSettingDTO saveUserSetting(UserSettingRequest userSettingRequest) {
		String openId = userSettingRequest.getOpenId();
		UserSettingDTO setting = userSettingRequest.getUserOption();

		return userService.saveUserSetting(openId, setting);
	}

	@GetMapping("/getUserSetting")
	public UserSettingDTO getUserSetting(@RequestParam("openId") String openId) {

		return userService.getUserSetting(openId);
	}

	@GetMapping("/getBindPlayers")
	public List<PlayerBriefVO> getBindPlayers(@RequestParam("openId") String openId) {
		return userService.getBindPlayers(openId);
	}
}
