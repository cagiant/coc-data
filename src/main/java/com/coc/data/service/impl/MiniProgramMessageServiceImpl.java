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
import com.coc.data.util.RedisKeyBuilder;
import com.coc.data.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author guokaiqiang
 * @date 2021/6/30 22:13
 */
@Service
@Slf4j
public class MiniProgramMessageServiceImpl implements MiniProgramMessageService {

	@Resource
	private WxConfig wxConfig;
	@Resource
	private RedisUtil redisUtil;

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
		Map<String, Integer> tempMap = new HashMap<>();
		Iterator<PlayerUserWarInfoDTO> iterator = memberRelatedUsers.iterator();
		while (iterator.hasNext()) {
			PlayerUserWarInfoDTO tempPlayer = iterator.next();
			if (tempMap.containsKey(tempPlayer.getOpenId())) {
				iterator.remove();
			} else {
				tempMap.put(tempPlayer.getOpenId(), 1);
			}
		}
		memberRelatedUsers.forEach(this::sendWarStartMessage);
	}

	@Override
	public void sendThreeStarMessage(WarInfoDTO warInfo) {
		String threeStarRedisKey = RedisKeyBuilder.buildThreeStarWarTagKey(warInfo.getTag());
		LocalDateTime startTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String startDateRedis = redisUtil.get(threeStarRedisKey);
		int minute = LocalDateTime.now().getMinute() / 5 * 5;
		// 本次定时任务起来的时间
		LocalDateTime taskTime = LocalDateTime.now().withMinute(minute).withSecond(0);
		if (ObjectUtils.isEmpty(startDateRedis)) {
			startTime = taskTime;
		} else {
			startTime = LocalDateTime.parse(startDateRedis, formatter);
		}

		// 获取这次新增的三星记录
		List<PlayerUserWarInfoDTO> memberRelatedUsers =
			userService.getThreeStarPlayerInfoInCertainTime(warInfo.getTag(),
				DateUtil.asDate(startTime));
		// 过滤掉不需要发信息的用户，同时每个用户，每个通知只发送一次
		memberRelatedUsers =
			memberRelatedUsers.stream().filter(this::userAcceptWarInfoMessage).collect(
				Collectors.collectingAndThen(Collectors.toCollection(
					() -> new TreeSet<>(Comparator.comparing(o -> String.format("%s-%s", o.getAttackOrder(), o.getOpenId())))
				),
				ArrayList::new
			));
		if (memberRelatedUsers.size() == 0) {
			redisUtil.setex(threeStarRedisKey, taskTime.format(formatter), 60 * 60 * 24);
			return;
		}
		Map<String, List<PlayerUserWarInfoDTO>> memberMap =
			memberRelatedUsers.stream().collect(Collectors.groupingBy(PlayerUserWarInfoDTO::getOpenId));
		memberMap.forEach((k, infos) -> {
			if (infos.size() == 1) {
				sendThreeStarMessage(infos.get(0));
			} else {
				sendThreeStarMessage(infos.get(0), infos.size());
			}
		});
		Optional<PlayerUserWarInfoDTO> maxCreateTimeInfo =
			memberRelatedUsers.stream().max(Comparator.comparing(PlayerUserWarInfoDTO::getCreateTime));
		maxCreateTimeInfo.ifPresent(playerUserWarInfoDTO -> redisUtil.setex(
			threeStarRedisKey,
			playerUserWarInfoDTO.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter),
			60 * 60 * 24)
		);
	}

	@Override
	public void sendWarEndMessage(WarInfoDTO warInfo) {
		// 等战争结果信息能查了再填写
	}

	void sendThreeStarMessage(PlayerUserWarInfoDTO u) {
		String title = String.format("%s 战况通报", u.getClanName());
		String msg = String.format("%s 三星对方 %s 号", u.getPlayerName(),
			u.getOpponentRankToAttack());
		try {
			sendWarResultMessage(
				title,
				msg,
				String.format("pages/war/index?warTag=%s&clanTag=%s",
					URLEncoder.encode(u.getWarTag(), "utf-8"),
					URLEncoder.encode(u.getClanTag(), "utf-8")
				),
				u.getOpenId());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	void sendThreeStarMessage(PlayerUserWarInfoDTO u, int threeStarCount) {
		String title = String.format("%s 战况通报", u.getClanName());
		String msg = String.format("新增 %s 个三星，点击下方链接查看", threeStarCount);
		try {
			sendWarResultMessage(
				title,
				msg,
				String.format("pages/war/index?warTag=%s&clanTag=%s",
					URLEncoder.encode(u.getWarTag(), "utf-8"),
					URLEncoder.encode(u.getClanTag(), "utf-8")
				),
				u.getOpenId());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	String getMessagePlayerName(String playerName) {
		return playerName.length() > 5 ? playerName.substring(0, 5) : playerName;
	}

	void sendWarStartMessage(PlayerUserWarInfoDTO u) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String title = "部落战信息通告";
		String msg = String.format("%s 所在的部落20分钟内即将开战", getMessagePlayerName(u.getPlayerName()));
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
		String result = responseEntity.getBody();
		log.info(String.format("miniprogram message info: %s, result: %s", FormatUtil.serializeObject2JsonStr(msgDTO), result));

		return result;
	}

	String getAccessToken() {
		String miniProgramToken = redisUtil.get(RedisKeyBuilder.getMiniprogramToken());
		if (miniProgramToken == null) {
			RestTemplate restTemplate = new RestTemplate();
			Map<String, String> params = new HashMap<>();
			params.put("APPID", wxConfig.getAppId());
			params.put("APPSECRET", wxConfig.getAppSecret());
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
			String body = responseEntity.getBody();
			JSONObject object = JSON.parseObject(body);
			miniProgramToken = object.getString("access_token");
			redisUtil.setex(RedisKeyBuilder.getMiniprogramToken(), miniProgramToken,
				Integer.parseInt(object.getString("expires_in")));
		}

		return miniProgramToken;
	}
}
