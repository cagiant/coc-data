package com.coc.data.service;

import com.coc.data.dto.WarInfoDTO;

/**
 * @author guokaiqiang
 * @date 2021/6/30 22:13
 */
public interface MiniProgramMessageService {

	String sendWarInfoMessage(String title, String msg, String openId, String pageUrl,
	                          String endDate,
	                          String from);

	String sendWarResultMessage(String title, String msg, String pageUrl, String openId);

	void sendClanLeagueStartMessage(WarInfoDTO warInfo);

	void sendWarStartMessage(WarInfoDTO warInfo);

	void sendThreeStarMessage(WarInfoDTO warInfo);

	void sendWarEndMessage(WarInfoDTO warInfo);
}
