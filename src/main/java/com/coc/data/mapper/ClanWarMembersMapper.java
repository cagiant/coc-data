package com.coc.data.mapper;

import com.coc.data.model.ClanWarMembers;
import com.coc.data.model.ClanWarMembersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClanWarMembersMapper {

    void insertIgnoreExist(@Param("list") List<ClanWarMembers> clanWarMemberDOList);
}