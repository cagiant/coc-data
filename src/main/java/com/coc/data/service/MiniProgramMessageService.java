package com.coc.data.service;

/**
 * @author guokaiqiang
 * @date 2021/6/30 22:13
 */
public interface MiniProgramMessageService {

	String sendWarInfoMessage(String title, String msg, String openId, String pageUrl,
	                          String endDate,
	                          String from);

	String sendWarResultMessage(String title, String msg, String pageUrl, String openId);

}
