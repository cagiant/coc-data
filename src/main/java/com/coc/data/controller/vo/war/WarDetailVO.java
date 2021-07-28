package com.coc.data.controller.vo.war;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/7/10 21:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarDetailVO {

	private String clanName;

	private String clanTag;

	private String clanIconUrl;

	private String opponentClanName;

	private String opponentClanTag;

	private String opponentClanIconUrl;

	private Long stars;

	private BigDecimal destructionPercentage;

	private Long opponentStars;

	private BigDecimal opponentDestructionPercentage;

	private Long warTimeLeft;


	private String state;

	private String stateMsg;

	private List<WarLogVO> warLogs;

	private List<WarLogVO> recentThreeStarWarLogs;
}
