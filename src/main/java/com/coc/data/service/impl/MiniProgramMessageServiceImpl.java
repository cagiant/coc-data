package com.coc.data.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coc.data.constant.WxConfig;
import com.coc.data.dto.user.MiniProgramMessageDTO;
import com.coc.data.enums.MiniProgramTemplateEnum;
import com.coc.data.service.MiniProgramMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guokaiqiang
 * @date 2021/6/30 22:13
 */
@Service
public class MiniProgramMessageServiceImpl implements MiniProgramMessageService {

	@Resource
	private WxConfig wxConfig;

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
