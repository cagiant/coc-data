package com.coc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaiqiang
 * @date 2020/8/1 11:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClanMemberDTO {

    private String tag;

    private String name;

    private String role;

    private Long expLevel;

    private LeagueDTO league;

    private Long trophies;

    private Long versusTrophies;

    private Long clanRank;

    private Long previousClanRank;

    private Long donations;

    private Long donationsReceived;

    @Data
    class LeagueDTO {

        private Long id;

        private String name;
    }
}
