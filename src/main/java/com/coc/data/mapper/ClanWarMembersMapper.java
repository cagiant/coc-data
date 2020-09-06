package com.coc.data.mapper;

import com.coc.data.model.ClanWarMembers;
import com.coc.data.model.ClanWarMembersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClanWarMembersMapper {

    void insertIgnoreExist(@Param("list") List<ClanWarMembers> clanWarMemberDOList);

    List<ClanWarMembers> getWarLogRelatedClanWarMembers(
        @Param("attackerTag") String attackerTag,
        @Param("defenderTag") String defenderTag,
        @Param("warTag") String warTag,
        @Param("clanTag") String clanTag);

    void updateClanWarMember(@Param("member") ClanWarMembers clanWarMember);

    List<ClanWarMembers> getCurrentSeasonData(@Param("season") String season);

    void deleteNotInClanWarMember(@Param("warTag") String tag, @Param("clanTag") String clanTag, @Param("list") List<String> clanWarMemberTagList);
}