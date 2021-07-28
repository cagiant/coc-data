package com.coc.data.controller.vo.war;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author guokaiqiang
 * @date 2021/7/10 21:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarLogVO {

	private String attackerTag;

	private String attackerName;

	private Long attackerMapPosition;

	private String defenderTag;

	private String defenderName;

	private Long defenderMapPosition;

	/**
	 * 是否我方进攻，在查询指定部落的信息时有意义
	 **/
	private Boolean isAttack;

	/**
	 * 时间锚，即日志距离当前时间已经发生了多久
	 **/
	private String timeAnchor;

	private Date createTime;

	private Long attackStar;

	private String wxKey;

	private Long destructionPercentage;
}
