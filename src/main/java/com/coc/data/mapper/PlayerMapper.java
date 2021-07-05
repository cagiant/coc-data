package com.coc.data.mapper;

import com.coc.data.controller.vo.user.PlayerBriefVO;
import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.Player;
import com.coc.data.model.base.PlayerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayerMapper extends BaseDao<Player, PlayerExample> {

    void batchInsert(List<Player> playersList);

	Player selectByTag(String playerTag);

	List<PlayerBriefVO> selectBriefPlayer(@Param("openId") String openId);

	void batchDelete(@Param("list") List<Player> playerList);
}