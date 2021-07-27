package com.coc.data.mapper;

import com.coc.data.dto.user.PlayerUserWarInfoDTO;
import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.User;
import com.coc.data.model.base.UserExample;
import com.coc.data.model.base.UserWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper extends BaseDao<UserWithBLOBs, UserExample> {

	UserWithBLOBs selectValidUser(@Param("openId") String openId);

	void insertOnDuplicateKeyUpdate(@Param("user") User user);

	UserWithBLOBs selectByOpenId(@Param("openId") String openId);

	void saveUserSetting(@Param("openId") String openId,
	                     @Param("setting") String setting);

	List<PlayerUserWarInfoDTO> selectByMemberTags(@Param("list") List<String> memberTags);

	List<PlayerUserWarInfoDTO> getThreeStarPlayerInfoInCertainTime(@Param("warTag") String warTag,
	                                                               @Param("date") Date startDate);
}