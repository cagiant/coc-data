package com.coc.data.mapper;

import com.coc.data.model.Clans;

import org.apache.ibatis.annotations.Param;

public interface ClansMapper {

    void insertOnDuplicateKeyUpdate(@Param("clan") Clans clan);
}