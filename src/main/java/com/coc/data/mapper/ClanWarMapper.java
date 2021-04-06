package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.ClanWar;
import com.coc.data.model.base.ClanWarExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ClanWarMapper extends BaseDao<ClanWar, ClanWarExample> {

    int getUnStartedClanWar(@Param("date") Date date, @Param("clanTag") String clanTag);

    void insertOnDuplicateKeyUpdate(@Param("warInfo") ClanWar currentWar);

    List<ClanWar> getWarsByWarTagList(List<String> warTags);

	List<ClanWar> getUnendedLeagueWarByClanTag(@Param("tag") String tag);
}