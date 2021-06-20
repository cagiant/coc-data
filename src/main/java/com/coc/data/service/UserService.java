package com.coc.data.service;

import com.coc.data.controller.request.user.WxUserProfileRequest;
import com.coc.data.dto.user.WxUserInfoDTO;

/**
 * @author guokaiqiang
 * @date 2021/6/14 21:35
 */
public interface UserService {

	WxUserInfoDTO miniProgramLogin(String code);

	WxUserInfoDTO refreshUser(WxUserProfileRequest userProfileRequest);
}
