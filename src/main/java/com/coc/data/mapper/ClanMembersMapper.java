package com.coc.data.mapper;

import com.coc.data.model.ClanMembers;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClanMembersMapper {

    void insertOnDuplicateKeyUpdate(@Param("list") List<ClanMembers> clanMembersList);
}