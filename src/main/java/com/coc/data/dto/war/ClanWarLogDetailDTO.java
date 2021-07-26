package com.coc.data.dto.war;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author guokaiqiang
 * @date 2021/7/11 09:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanWarLogDetailDTO {

	private String attackerTag;

	private String attackerClanTag;

	private String attackerName;

	private Long attackerMapPosition;

	private String defenderTag;

	private String defenderClanTag;

	private String defenderName;

	private Long defenderMapPosition;

	private Date createTime;

	private Long star;

	private Long destructionPercentage;

	private Long attackOrder;

	private String warTag;

	private Boolean isBestAttack;
}
