package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.Clan;
import com.coc.data.model.base.ClanExample;
import org.apache.ibatis.annotations.Param;

public interface ClanMapper extends BaseDao<Clan, ClanExample> {

    void insertOnDuplicateKeyUpdate(@Param("clan") Clan clan);

    Clan selectByClanTag(@Param("tag") String clanTag);
}