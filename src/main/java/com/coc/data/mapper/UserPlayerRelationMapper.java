package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.UserPlayerRelation;
import com.coc.data.model.base.UserPlayerRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPlayerRelationMapper extends BaseDao<UserPlayerRelation, UserPlayerRelationExample> {

    UserPlayerRelation getUserPlayerRelation(@Param("userId") Long id, @Param("playerId") Long id2);
}