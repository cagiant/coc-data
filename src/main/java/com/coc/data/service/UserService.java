package com.coc.data.service;

import com.coc.data.controller.request.user.WxUserProfileRequest;
import com.coc.data.controller.vo.user.MiniProgramBindPlayerVO;
import com.coc.data.controller.vo.user.PlayerBriefVO;
import com.coc.data.dto.PlayerDTO;
import com.coc.data.dto.user.WxUserInfoDTO;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/6/14 21:35
 */
public interface UserService {

	WxUserInfoDTO miniProgramLogin(String code);

	WxUserInfoDTO refreshUser(WxUserProfileRequest userProfileRequest);

	PlayerDTO getPlayerInfo(String tag);

	MiniProgramBindPlayerVO bindPlayer(String openId, String playerTag);

	List<PlayerBriefVO> getBindPlayers(String openId);
}
