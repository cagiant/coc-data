package com.coc.data.service.impl;

import com.coc.data.controller.vo.ReportDataVO;
import com.coc.data.controller.vo.ReportOptionVO;
import com.coc.data.controller.vo.ReportOptionVO.*;
import com.coc.data.mapper.ClansMapper;
import com.coc.data.mapper.ReportClanWarMemberMapper;
import com.coc.data.model.Clans;
import com.coc.data.service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author guokaiqiang
 * @date 2021/3/25 22:10
 */
@Service
public class ReportServiceImpl implements ReportService {

	@Resource
	private ClansMapper clansMapper;
	@Resource
	private ReportClanWarMemberMapper reportClanWarMemberMapper;


	@Override
	public ReportOptionVO getLeagueGroupWarDataClanInfo() {
		List<Clans> clanList = clansMapper.getClansNeedLeagueReport();
		List<String> seasons = reportClanWarMemberMapper.getSeasons();

		return buildVO(clanList, seasons);
	}

	@Override
	public ReportOptionVO getNormalWarDataClanInfo() {
		List<Clans> clanList = clansMapper.getClansNeedNormalWarReport();
		List<String> seasons = reportClanWarMemberMapper.getSeasons();

		return buildVO(clanList, seasons);
	}

	@Override
	public ReportDataVO getReportWarData(String tag, String season, Boolean league) {
		List<Map> data = reportClanWarMemberMapper.getReportWarData(tag, season, league);

		return ReportDataVO.builder()
			.detail(data)
			.season(season)
			.build();
	}

	ReportOptionVO buildVO(List<Clans> clanList, List<String> seasons) {
		ReportOptionVO vo = new ReportOptionVO();
		for (Clans clans : clanList) {
			vo.getOptions().add(ClanOption.builder()
				.name(clans.getName())
				.tag(clans.getTag())
				.build());
		}
		for (String season : seasons) {
			vo.getSeasonOptions().add(SeasonOption.builder()
				.name(season)
				.season(season)
				.build());
		}
		return vo;
	}
}
