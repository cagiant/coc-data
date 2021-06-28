package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.constant.WxConfig;
import com.coc.data.controller.convert.WxMappingJackson2HttpMessageConverter;
import com.coc.data.controller.request.user.WxUserProfileRequest;
import com.coc.data.controller.vo.user.MiniProgramBindPlayerVO;
import com.coc.data.controller.vo.user.PlayerBriefVO;
import com.coc.data.dto.PlayerDTO;
import com.coc.data.dto.user.UserSettingDTO;
import com.coc.data.dto.user.WxCode2SessionDTO;
import com.coc.data.dto.user.WxUserInfoDTO;
import com.coc.data.mapper.PlayerMapper;
import com.coc.data.mapper.UserMapper;
import com.coc.data.mapper.UserPlayerRelationMapper;
import com.coc.data.model.base.*;
import com.coc.data.service.UserService;
import com.coc.data.util.FormatUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

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
	@Resource
	private PlayerMapper playerMapper;
	@Resource
	private UserPlayerRelationMapper userPlayerRelationMapper;

	@Resource
	private CocApiHttpClient httpClient;

	private static final String MINIPROGRAM_CODE2SESSION_URL = "https://api.weixin.qq" +
		".com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

	@Override
	public WxUserInfoDTO miniProgramLogin(String code) {
		WxCode2SessionDTO sessionResult = getSessionResult(code);

		UserWithBLOBs user = userMapper.selectValidUser(sessionResult.getOpenId(),
			sessionResult.getSessionKey());

		if (user == null) {
			// 说明session过期了或者是从未登录过，需要前端刷新当前用户的用户信息
			return null;
		}

		return user2WxUserInfo(user);
	}

	private WxUserInfoDTO user2WxUserInfo(UserWithBLOBs user) {
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

		UserWithBLOBs userWithBLOBs = new UserWithBLOBs();
		BeanUtils.copyProperties(user, userWithBLOBs);
		userWithBLOBs.setAvatarUrl(userProfileRequest.getAvatarUrl());

		userMapper.insertOnDuplicateKeyUpdate(userWithBLOBs);

		return user2WxUserInfo(userWithBLOBs);
	}

	@Override
	public PlayerDTO getPlayerInfo(String tag) {
		return httpClient.getPlayerInfoByTag(tag);
	}

	@Override
	public MiniProgramBindPlayerVO bindPlayer(String openId, String playerTag) {
		User user = userMapper.selectByOpenId(openId);

		if (ObjectUtils.isEmpty(user)) {
			return MiniProgramBindPlayerVO.builder()
				.bindSuccess(false)
				.msg(String.format("不存在的用户：%s", openId))
				.build();
		}

		try {
			Player player =  playerMapper.selectByTag(playerTag);
			if (ObjectUtils.isEmpty(player)) {
				PlayerDTO playerDTO = getPlayerInfo(playerTag);
				player = Player.builder()
					.tag(playerTag)
					.name(playerDTO.getName())
					.build();
				playerMapper.insertSelective(player);
			}
			UserPlayerRelation relation =
				userPlayerRelationMapper.getUserPlayerRelation(user.getId(), player.getId());
			if (relation != null) {
				return MiniProgramBindPlayerVO.builder()
					.bindSuccess(false)
					.msg(String.format("标签%s已经被绑定",  playerTag))
					.build();
			}
			UserPlayerRelation userPlayerRelation = UserPlayerRelation.builder()
				.userId(user.getId())
				.playerId(player.getId())
				.build();
			userPlayerRelationMapper.insertSelective(userPlayerRelation);

			return MiniProgramBindPlayerVO.builder()
				.bindSuccess(true)
				.build();
		} catch (Exception e) {
			return MiniProgramBindPlayerVO.builder()
				.bindSuccess(false)
				.msg(e.getMessage())
				.build();
		}
	}

	@Override
	public List<PlayerBriefVO> getBindPlayers(String openId) {
		return playerMapper.selectBriefPlayer(openId);
	}

	@Override
	public MiniProgramBindPlayerVO unbindPlayer(String openId, String playerTag) {
		User user = userMapper.selectByOpenId(openId);

		if (ObjectUtils.isEmpty(user)) {
			return MiniProgramBindPlayerVO.builder()
				.bindSuccess(false)
				.msg(String.format("不存在的用户：%s", openId))
				.build();
		}

		Player player = playerMapper.selectByTag(playerTag);
		if (ObjectUtils.isEmpty(player)) {
			return MiniProgramBindPlayerVO.builder()
				.bindSuccess(false)
				.msg(String.format("不存在的首领：%s", playerTag))
				.build();
		}

		UserPlayerRelationExample example = new UserPlayerRelationExample();
		example.createCriteria().andUserIdEqualTo(user.getId()).andPlayerIdEqualTo(player.getId()).andIsDeletedEqualTo((byte) 0);
		UserPlayerRelation record = new UserPlayerRelation();
		record.setIsDeleted((byte) 1);
		userPlayerRelationMapper.updateByExampleSelective(record, example);

		return MiniProgramBindPlayerVO.builder()
			.bindSuccess(true)
			.build();
	}

	@Override
	public UserSettingDTO saveUserSetting(String openId, UserSettingDTO setting) {
		userMapper.saveUserSetting(openId, FormatUtil.serializeObject2JsonStr(setting));

		return setting;
	}

	@Override
	public UserSettingDTO getUserSetting(String openId) {
		UserWithBLOBs user = userMapper.selectByOpenId(openId);

		return FormatUtil.deserializeCamelCaseJson2Object(user.getSetting(), UserSettingDTO.class);
	}

	WxCode2SessionDTO getSessionResult(String code) {
		String url = String.format(MINIPROGRAM_CODE2SESSION_URL,wxConfig.getAppId(),
			wxConfig.getAppSecret(), code);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());

		return restTemplate.getForObject(url, WxCode2SessionDTO.class);
	}
}
