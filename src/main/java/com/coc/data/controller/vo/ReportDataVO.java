package com.coc.data.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author guokaiqiang
 * @date 2021/3/25 22:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDataVO {

	private String season;

	private List<Map> detail;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberReport {

		private String name;

		private Long attackNoStar;

		private Long attackOneStar;

		private Long attackTwoStar;

		private Long attackThreeStar;

		private Long defenseNoStar;

		private Long defenseOneStar;

		private Long defenseTwoStar;

		private Long defenseThreeStar;
	}
}
