package com.coc.data.service;

import com.coc.data.controller.vo.ReportDataVO;
import com.coc.data.controller.vo.ReportOptionVO;

/**
 * @author guokaiqiang
 * @date 2021/3/25 22:09
 */
public interface ReportService {

	/**
	 * 获取联赛相关的报表选项
	 * @param
	 * @return com.coc.data.controller.vo.ReportOptionVO
	 * @author guokaiqiang
	 * @date 2021/3/25 22:11
	 **/
	ReportOptionVO getLeagueGroupWarDataClanInfo();

	/**
	 * 获取日常部落战相关的报表选项
	 * @param
	 * @return com.coc.data.controller.vo.ReportOptionVO
	 * @author guokaiqiang
	 * @date 2021/3/25 22:18
	 **/
	ReportOptionVO getNormalWarDataClanInfo();

	/**
	 * 获取部落战相关数据
	 * @param tag
	 * @param season
	 * @param league
	 * @return com.coc.data.controller.vo.ReportDataVO
	 * @author guokaiqiang
	 * @date 2021/3/25 22:37
	 **/
	ReportDataVO getReportWarData(String tag, String season, Boolean league);
}
