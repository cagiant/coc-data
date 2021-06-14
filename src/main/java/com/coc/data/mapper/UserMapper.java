package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.User;
import com.coc.data.model.base.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseDao<User, UserExample> {

	User selectValidUser(@Param("openId") String openId, @Param("sessionKey") String sessionKey);
}