package com.coc.data.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author guokaiqiang
 * @date 2021/6/6 22:12
 */
@Data
@Configuration
public class WxConfig {

	@Value("${wx.appid}")
	private String appId;

	@Value("${wx.appsecret}")
	private String appSecret;

	public static String accessToken;

	public static Date tokenExipres;
}

