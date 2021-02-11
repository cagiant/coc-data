package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.Clan;
import com.coc.data.model.base.ClanExample;
import org.apache.ibatis.annotations.Param;

public interface ClanMapper extends BaseDao<Clan, ClanExample> {

    void insertOnDuplicateKeyUpdate(@Param("clan") Clan clan);
}