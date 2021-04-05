package com.coc.data.mapper.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 12:10
 */
public interface BaseDao<T, E> {
    long countByExample(E example);

    int deleteByExample(E example);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E example);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
