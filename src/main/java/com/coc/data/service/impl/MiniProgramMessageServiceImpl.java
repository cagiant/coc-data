package com.coc.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coc.data.constant.WxConfig;
import com.coc.data.dto.WarInfoDTO;
import com.coc.data.dto.user.MiniProgramMessageDTO;
import com.coc.data.dto.user.PlayerUserWarInfoDTO;
import com.coc.data.dto.user.UserSettingDTO;
import com.coc.data.enums.MiniProgramTemplateEnum;
import com.coc.data.enums.MiniprogramMessageSenderEnum;
import com.coc.data.service.MiniProgramMessageService;
import com.coc.data.service.UserService;
import com.coc.data.util.DateUtil;
import com.coc.data.util.FormatUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author guokaiqiang
 * @date 2021/6/30 22:13
 */
@Service
public class MiniProgramMessageServiceImpl implements MiniProgramMessageService {

	@Resource
	private WxConfig wxConfig;

	@Resource
	private UserService userService;

	@Override
	public String sendWarInfoMessage(String title, String msg, String openId,
	                                 String pageUrl, String endDate, String from) {
		Map<String, MiniProgramMessageDTO.TemplateData> map = new HashMap<>();
		map.put("thing1", new MiniProgramMessageDTO.TemplateData(title));
		map.put("thing2", new MiniProgramMessageDTO.TemplateData(msg));
		map.put("time3", new MiniProgramMessageDTO.TemplateData(endDate));
		map.put("thing4", new MiniProgramMessageDTO.TemplateData("System"));

		return sendMessage(
			MiniProgramMessageDTO.builder()
				.data(map)
				.openId(openId)
				.templateId(MiniProgramTemplateEnum.WAR_INFO.templateId)
				.page(pageUrl == null ? "pages/report/index" : pageUrl)
				.build()
		);
	}

	@Override
	public String sendWarResultMessage(String title, String msg, String pageUrl, String openId) {
		Map<String, MiniProgramMessageDTO.TemplateData> map = new HashMap<>();
		map.put("thing1", new MiniProgramMessageDTO.TemplateData(title));
		map.put("thing2", new MiniProgramMessageDTO.TemplateData(msg));
		return sendMessage(
			MiniProgramMessageDTO.builder()
				.data(map)
				.openId(openId)
				.templateId(MiniProgramTemplateEnum.WAR_RESULT.templateId)
				.page(pageUrl == null ? "pages/report/index" : pageUrl)
				.build()
		);
	}

	@Override
	public void sendClanLeagueStartMessage(WarInfoDTO warInfo) {
		List<PlayerUserWarInfoDTO> memberRelatedUsers = userService.getWarRelatedUsers(warInfo);
		memberRelatedUsers = memberRelatedUsers.stream().filter(this::userAcceptWarInfoMessage).collect(Collectors.toList());
		if (memberRelatedUsers.size() == 0) {
			return;
		}
		memberRelatedUsers.forEach(this::sendClanLeagueStartMessage);
	}

	@Override
	public void sendWarStartMessage(WarInfoDTO warInfo) {
		List<PlayerUserWarInfoDTO> memberRelatedUsers = userService.getWarRelatedUsers(warInfo);
		memberRelatedUsers = memberRelatedUsers.stream().filter(this::userAcceptWarInfoMessage).collect(Collectors.toList());
		if (memberRelatedUsers.size() == 0) {
			return;
		}
		memberRelatedUsers.forEach(this::sendWarStartMessage);
	}

	@Override
	public void sendThreeStarMessage(WarInfoDTO warInfo) {
		// 获取这次新增的三星记录
		int minute = LocalDateTime.now().getMinute() / 5 * 5;
		List<PlayerUserWarInfoDTO> memberRelatedUsers =
			userService.getThreeStarPlayerInfoInCertainTime(warInfo.getTag(),
				DateUtil.asDate(LocalDateTime.now().withMinute(minute).withSecond(0)));
		memberRelatedUsers = memberRelatedUsers.stream().filter(this::userAcceptWarInfoMessage).collect(Collectors.toList());
		if (memberRelatedUsers.size() == 0) {
			return;
		}
		memberRelatedUsers.forEach(this::sendThreeStartMessage);
	}

	void sendThreeStartMessage(PlayerUserWarInfoDTO u) {
		String title = String.format("%s 战况通报", u.getClanName());
		String msg = String.format("%s 三星对方 %s 号", u.getPlayerName(),
			u.getOpponentRankToAttack());
		sendWarResultMessage(title, msg, null, u.getOpenId());
	}

	String getMessagePlayerName(String playerName) {
		return playerName.length() > 5 ? playerName.substring(0, 5) : playerName;
	}

	void sendWarStartMessage(PlayerUserWarInfoDTO u) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String title = "部落战即将开始";
		String msg = String.format("%s 参加的部落战即将在20分钟内开始", getMessagePlayerName(u.getPlayerName()));
		sendWarInfoMessage(title, msg, u.getOpenId(), null,
			format.format(new Date()), MiniprogramMessageSenderEnum.SYSTEM.code);
	}

	void sendClanLeagueStartMessage(PlayerUserWarInfoDTO u) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String title = "联赛开始通知";
		String msg = String.format("%s参加的部落联赛已经开始", getMessagePlayerName(u.getPlayerName()));
		sendWarInfoMessage(title, msg,u.getOpenId(), null,
			format.format(new Date()), MiniprogramMessageSenderEnum.SYSTEM.code);
	}

	boolean userAcceptWarInfoMessage(PlayerUserWarInfoDTO u) {
		String userSetting = u.getUserSetting();
		if (StringUtils.isEmpty(userSetting)) {
			return false;
		}
		UserSettingDTO dto = FormatUtil.deserializeCamelCaseJson2Object(userSetting,
			UserSettingDTO.class);

		return dto.getWarInfoMessage() != null && dto.getWarInfoMessage();
	}

	String sendMessage(MiniProgramMessageDTO msgDTO) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
		ResponseEntity<String> responseEntity =
			restTemplate.postForEntity(url, msgDTO, String.class);

		return responseEntity.getBody();
	}

	String getAccessToken() {
		if (WxConfig.tokenExipres != null && WxConfig.tokenExipres.before(new Date())) {
			return WxConfig.accessToken;
		}
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		params.put("APPID", wxConfig.getAppId());
		params.put("APPSECRET", wxConfig.getAppSecret());
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
			"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
		String body = responseEntity.getBody();
		JSONObject object = JSON.parseObject(body);
		WxConfig.accessToken = object.getString("access_token");
		WxConfig.tokenExipres = new Date(System.currentTimeMillis() + Long.parseLong(object.getString("expires_in")));

		return WxConfig.accessToken;
	}
}
