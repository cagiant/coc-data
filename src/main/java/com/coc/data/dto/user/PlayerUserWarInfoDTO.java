package com.coc.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author guokaiqiang
 * @date 2021/7/1 12:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerUserWarInfoDTO {

	private String playerName;

	private String userName;

	private String playerTag;

	private String openId;

	private String userSetting;

	private String attackOrder;

	private String opponentRankToAttack;

	private String clanName;

	private Date createTime;

	private String clanTag;

	private String warTag;
}
