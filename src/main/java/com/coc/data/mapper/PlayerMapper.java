package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.Player;
import com.coc.data.model.base.PlayerExample;

import java.util.List;

public interface PlayerMapper extends BaseDao<Player, PlayerExample> {

    void batchInsert(List<Player> playersList);
}