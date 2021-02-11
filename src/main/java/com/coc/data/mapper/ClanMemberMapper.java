package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.ClanMember;
import com.coc.data.model.base.ClanMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClanMemberMapper extends BaseDao<ClanMember, ClanMemberExample> {

    void batchInsert(@Param("list") List<ClanMember> clanMembersList);
}