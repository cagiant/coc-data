package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.ClanWar;
import com.coc.data.model.ClanWars;
import com.coc.data.model.base.ClanWarExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface ClanWarMapper extends BaseDao<ClanWar, ClanWarExample> {

    int getUnStartedClanWar(@Param("date") Date date, @Param("clanTag") String clanTag);

    void insertOnDuplicateKeyUpdate(@Param("warInfo") ClanWars currentWar);
}