package com.coc.data.service;

import com.coc.data.controller.request.user.WxUserProfileRequest;
import com.coc.data.controller.vo.user.MiniProgramBindPlayerVO;
import com.coc.data.controller.vo.user.PlayerBriefVO;
import com.coc.data.dto.PlayerDTO;
import com.coc.data.dto.WarInfoDTO;
import com.coc.data.dto.user.PlayerUserWarInfoDTO;
import com.coc.data.dto.user.UserSettingDTO;
import com.coc.data.dto.user.WxUserInfoDTO;

import java.util.Date;
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

	MiniProgramBindPlayerVO unbindPlayer(String openId, String playerTag);

	UserSettingDTO saveUserSetting(String openId, UserSettingDTO setting);

	UserSettingDTO getUserSetting(String openId);

	List<PlayerUserWarInfoDTO> getWarRelatedUsers(WarInfoDTO warInfo);

	List<PlayerUserWarInfoDTO> getThreeStarPlayerInfoInCertainTime(String warTag, Date startDate);
}
