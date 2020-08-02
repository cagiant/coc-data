package com.coc.data.mapper;

import com.coc.data.model.ClanWars;
import com.coc.data.model.ClanWarsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClanWarsMapper {

    void insertOnDuplicateKeyUpdate(@Param("warInfo") ClanWars currentWar);
}