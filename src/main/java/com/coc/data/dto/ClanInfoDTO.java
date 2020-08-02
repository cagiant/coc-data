package com.coc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/7/30 22:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanInfoDTO {

    private String tag;

    private String name;

    private String type;

    private String description;

    private Long clanLevel;

    private Long clanPoints;

    private Long clanVersusPoints;

    private Long requiredTrophies;

    private String warFrequency;

    private Long warWinStreak;

    private Long warWins;

    private Long warTies;

    private Long warLosses;

    private Boolean isWarLogPublic;

    private WarLeagueDTO warLeague;

    private ClanLocationDTO location;

    private IconUrlDTO badgeUrls;

    private Long members;

    private List<ClanMemberDTO> memberList;

    private List<ClanLabelDTO> labels;
}
