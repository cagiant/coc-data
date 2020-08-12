package com.coc.data.mapper;

import com.coc.data.model.ClanWarLogs;
import com.coc.data.model.ClanWarLogsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClanWarLogsMapper {

    void insertIgnoreExist(@Param("list") List<ClanWarLogs> clanWarLogList);

    List<ClanWarLogs> getUncalculatedLogs();

    void setClanWarLogCalaulated(@Param("id") Long id);
}