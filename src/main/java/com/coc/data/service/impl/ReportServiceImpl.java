package com.coc.data.service.impl;

import com.coc.data.controller.request.ReportDataRequest;
import com.coc.data.controller.vo.ReportDataVO;
import com.coc.data.controller.vo.ReportOptionVO;
import com.coc.data.controller.vo.ReportOptionVO.*;
import com.coc.data.mapper.*;
import com.coc.data.model.base.Clan;
import com.coc.data.model.base.ClanWarLog;
import com.coc.data.model.base.ClanWarMember;
import com.coc.data.service.ReportService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author guokaiqiang
 * @date 2021/3/25 22:10
 */
@Service
public class ReportServiceImpl implements ReportService {

	@Resource
	private ReportClanWarMemberMapper reportClanWarMemberMapper;

	@Resource
	private ClanMapper clanMapper;
	@Resource
	private ClanWarMapper clanWarMapper;
	@Resource
	private ClanWarLogMapper clanWarLogMapper;
	@Resource
	private ClanWarMemberMapper clanWarMemberMapper;


	@Override
	public ReportOptionVO getLeagueGroupWarDataClanInfo() {
		List<Clan> clanList = clanMapper.getClansNeedLeagueReport();
		List<String> seasons = reportClanWarMemberMapper.getSeasons();

		return buildVO(clanList, seasons);
	}

	@Override
	public ReportOptionVO getNormalWarDataClanInfo() {
		List<Clan> clanList = clanMapper.getClansNeedNormalWarReport();
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

	@Override
	public ReportOptionVO getReportOption() {
		List<Clan> clanList = clanMapper.getNormalClans();
		List<String> seasons = clanWarMapper.getSeasons();

		return buildVO(clanList, seasons);
	}

	@Override
	public ReportDataVO getReportList(ReportDataRequest request) {
		// 获取满足条件的所有对战记录
		List<ClanWarLog> clanWarLogList = clanWarLogMapper.getWarLogs(request);
		List<String> warTags = clanWarLogList.stream().map(ClanWarLog::getWarTag).distinct().collect(Collectors.toList());
		List<ClanWarMember> clanWarMemberList = clanWarMemberMapper.getClanWarMemberInfo(warTags);
		Map<String, List<ClanWarLog>> attackerMap =
			clanWarLogList.stream().collect(Collectors.groupingBy(ClanWarLog::getAttackerTag));
		Map<String, List<ClanWarLog>> defenderMap =
			clanWarLogList.stream().collect(Collectors.groupingBy(ClanWarLog::getDefenderTag));
		Map<String, List<ClanWarLog>> memberWarLogListMap = Maps.newLinkedHashMap();
		for (ClanWarMember clanWarMember : clanWarMemberList) {
			String memberTag = clanWarMember.getMemberTag();
			memberWarLogListMap.put(memberTag, attackerMap.getOrDefault(memberTag,
				new ArrayList<>()));
			memberWarLogListMap.get(memberTag).addAll(defenderMap.getOrDefault(memberTag,
				new ArrayList<>()));
		}
		Map<String, ClanWarMember> clanWarMemberMap =
			clanWarMemberList.stream().collect(Collectors.toMap(ClanWarMember::getMemberTag, s -> s));
		List<ReportDataVO.MemberReport> reportList = Lists.newLinkedList();
		memberWarLogListMap.forEach((memberTag, warLogList) -> {
			reportList.add(ReportDataVO.MemberReport.compute(warLogList, clanWarMemberMap.get(memberTag)));
		});

		return ReportDataVO.builder()
			.season(request.getSeason())
			.reports(reportList)
			.build();
	}

	ReportOptionVO buildVO(List<Clan> clanList, List<String> seasons) {
		ReportOptionVO vo = new ReportOptionVO();
		vo.setOptions(Lists.newLinkedList());
		vo.setSeasonOptions(Lists.newLinkedList());
		for (Clan clans : clanList) {
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
		vo.setClans(clanList);

		return vo;
	}
}
