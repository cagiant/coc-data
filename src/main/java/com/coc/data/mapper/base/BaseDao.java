package com.coc.data.mapper.base;

import com.coc.data.model.base.Clan;
import com.coc.data.model.base.ClanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 12:10
 */
public interface BaseDao<T, E> {
    long countByExample(ClanExample example);

    int deleteByExample(ClanExample example);

    int insert(Clan record);

    int insertSelective(Clan record);

    List<Clan> selectByExample(ClanExample example);

    int updateByExampleSelective(@Param("record") Clan record, @Param("example") ClanExample example);

    int updateByExample(@Param("record") Clan record, @Param("example") ClanExample example);
}
