package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.UserClanRelation;
import com.coc.data.model.base.UserClanRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserClanRelationMapper  extends BaseDao<UserClanRelation, UserClanRelationExample> {

	void batchInsert(@Param("list") List<UserClanRelation> relations);
}