package com.coc.data.mapper;

import com.coc.data.model.Players;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/10/9 21:45
 */
public interface PlayersMapper {

    void insertOnDuplicateKeyUpdate(@Param("list") List<Players> playersList);
}
