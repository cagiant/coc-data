package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.ClanWarMember;
import com.coc.data.model.base.ClanWarMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClanWarMemberMapper extends BaseDao<ClanWarMember, ClanWarMemberExample> {

    void batchInsert(@Param("list") List<ClanWarMember> currentClanWarMemberDOList);

    void deleteNotInClanWarMember(String tag, String clanTag, List<String> clanWarMemberTagList);
}