package com.coc.data.service.impl;

import com.coc.data.constant.WxConfig;
import com.coc.data.controller.convert.WxMappingJackson2HttpMessageConverter;
import com.coc.data.controller.request.user.WxUserProfileRequest;
import com.coc.data.dto.user.WxCode2SessionDTO;
import com.coc.data.dto.user.WxUserInfoDTO;
import com.coc.data.mapper.UserMapper;
import com.coc.data.model.base.User;
import com.coc.data.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author guokaiqiang
 * @date 2021/6/14 21:35
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private WxConfig wxConfig;
	@Resource
	private UserMapper userMapper;

	private static final String MINIPROGRAM_CODE2SESSION_URL = "https://api.weixin.qq" +
		".com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

	@Override
	public WxUserInfoDTO miniProgramLogin(String code) {
		WxCode2SessionDTO sessionResult = getSessionResult(code);

		User user = userMapper.selectValidUser(sessionResult.getOpenId(), sessionResult.getSessionKey());

		if (user == null) {
			// 说明session过期了或者是从未登录过，需要前端刷新当前用户的用户信息
			return null;
		}

		return user2WxUserInfo(user);
	}

	private WxUserInfoDTO user2WxUserInfo(User user) {
		return  WxUserInfoDTO.builder()
			.nickName(user.getNickName())
			.avatarUrl(user.getAvatarUrl())
			.city(user.getCity())
			.country(user.getCountry())
			.gender(user.getMale() ? 1: 0)
			.language(user.getLang())
			.province(user.getProvince())
			.unionId(user.getUnionId())
			.openId(user.getOpenId())
			.build();
	}

	@Override
	public WxUserInfoDTO refreshUser(WxUserProfileRequest userProfileRequest) {
		WxCode2SessionDTO sessionResult = getSessionResult(userProfileRequest.getCode());
		User user = User.builder()
			.avatarUrl(userProfileRequest.getAvatarUrl())
			.city(userProfileRequest.getCity())
			.country(userProfileRequest.getCountry())
			.lang(userProfileRequest.getLanguage())
			.male(userProfileRequest.getGender() == 1)
			.nickName(userProfileRequest.getNickName())
			.openId(sessionResult.getOpenId())
			.sessionKey(sessionResult.getSessionKey())
			.unionId(sessionResult.getUnionId())
			.province(userProfileRequest.getProvince())
			.build();

		userMapper.insertOnDuplicateKeyUpdate(user);

		return user2WxUserInfo(user);
	}

	WxCode2SessionDTO getSessionResult(String code) {
		String url = String.format(MINIPROGRAM_CODE2SESSION_URL,wxConfig.getAppId(),
			wxConfig.getAppSecret(), code);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());

		return restTemplate.getForObject(url, WxCode2SessionDTO.class);
	}
}
