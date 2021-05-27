package com.coc.data.controller.vo;

import com.coc.data.model.base.Clan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/3/25 22:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportOptionVO {

	/**
	 * 部落标签选项
	 **/
	private List<ClanOption> options;

	/**
	 * 赛季选项
	 **/
	private List<SeasonOption> seasonOptions;

	/**
	 * 部落选项
	 **/
	private List<Clan> clans;

	@Data
	@Builder
	public static class ClanOption {

		private String tag;

		private String name;
	}

	@Data
	@Builder
	public static class SeasonOption {

		private String season;

		private String name;
	}
}
