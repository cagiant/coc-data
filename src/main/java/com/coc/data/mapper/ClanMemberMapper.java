package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.ClanMember;
import com.coc.data.model.base.ClanMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClanMemberMapper extends BaseDao<ClanMember, ClanMemberExample> {

    void batchInsert(@Param("list") List<ClanMember> clanMembersList);

    void batchDelete(@Param("list") List<ClanMember> clanMembersList);
}