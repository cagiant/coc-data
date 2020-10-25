package com.coc.data.mapper;

import com.coc.data.model.ReportClanWarMember;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportClanWarMemberMapper {

    void replaceBatch(@Param("list") List<ReportClanWarMember> reportClanWarMemberList);
}