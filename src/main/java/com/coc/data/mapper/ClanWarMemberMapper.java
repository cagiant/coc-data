package com.coc.data.mapper;

import com.coc.data.mapper.base.BaseDao;
import com.coc.data.model.base.ClanWarMember;
import com.coc.data.model.base.ClanWarMemberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClanWarMemberMapper extends BaseDao<ClanWarMember, ClanWarMemberExample> {

    void batchInsert(@Param("list") List<ClanWarMember> currentClanWarMemberDOList);

    void deleteNotInClanWarMember(@Param("warTag") String tag,
                                  @Param("clanTag") String clanTag,
                                  @Param("list") List<String> clanWarMemberTagList);

	List<ClanWarMember> getClanWarMemberInfo(@Param("list") List<String> warTags, @Param("clanTag") String clanTag);

    void deleteNotInWarClanMember(@Param("warTag") String tag,
                                  @Param("list") List<String> inWarClanWarMemberList);

    List<ClanWarMember> selectClanMemberByWarTag(@Param("warTag") String warTag);
}