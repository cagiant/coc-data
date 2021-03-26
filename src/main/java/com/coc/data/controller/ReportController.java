package com.coc.data.controller;

import com.coc.data.controller.request.ReportDataRequest;
import com.coc.data.controller.vo.ReportDataVO;
import com.coc.data.controller.vo.ReportOptionVO;
import com.coc.data.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author guokaiqiang
 * @date 2021/3/25 21:57
 */
@RestController
@RequestMapping("")
public class ReportController {

	@Resource
	private ReportService reportService;

	@GetMapping("/leagueGroupWarDataClanInfo")
	public ReportOptionVO getLeagueGroupWarDataClanInfo() {
		return reportService.getLeagueGroupWarDataClanInfo();
	}

	@GetMapping("/currentWarDataClanInfo")
	public ReportOptionVO getNormalWarDataClanInfo() {
		return reportService.getNormalWarDataClanInfo();
	}

	@PostMapping("/currentWarData")
	public ReportDataVO getReportWarData(ReportDataRequest request) {
		String tag = request.getTag();
		String season = request.getSeason();
		Boolean league = request.getLeague() != null && request.getLeague();

		return reportService.getReportWarData(tag, season, league);
	}
}
