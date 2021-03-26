package com.coc.data.mapper;

import com.coc.data.model.ReportClanWarMember;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ReportClanWarMemberMapper {

    void replaceBatch(@Param("list") List<ReportClanWarMember> reportClanWarMemberList);

	List<String> getSeasons();

	List<Map> getReportWarData(@Param("tag") String tag,
	                           @Param("season") String season,
	                           @Param("league") Boolean league);
}