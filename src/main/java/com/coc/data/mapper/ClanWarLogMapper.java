package com.coc.data.mapper;

import com.coc.data.controller.request.ReportDataRequest;
import com.coc.data.dto.war.ClanWarLogDetailDTO;
import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.ClanWarLog;
import com.coc.data.model.base.ClanWarLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClanWarLogMapper extends BaseDao<ClanWarLog, ClanWarLogExample> {

    void batchInsert(@Param("list") List<ClanWarLog> clanWarLogList);

	List<ClanWarLog> getWarLogs(@Param("request") ReportDataRequest request);

	List<ClanWarLogDetailDTO> getWarLogDetail(@Param("warTag") String warTag);
}