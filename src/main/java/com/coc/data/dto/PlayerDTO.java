package com.coc.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/6/23 21:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

	private String tag;

	private String name;

	private Long townHallLevel;

	private Long townHallWeaponLevel;

	private Long expLevel;

	private Long trophies;

	private Long bestTrophies;

	private Long warStars;

	private Long attackWins;

	private Long defenseWins;

	private Long builderHallLevel;

	private Long versusTrophies;

	private Long bestVersusTrophies;

	private Long versusBattleWins;

	private String role;

	private Long donations;

	private Long donationsReceived;

	private Long versusBattleWinCount;

	private PlayerClanInfoDTO clan;

	private PlayerLeagueInfoDTO league;

	private PlayerLegendStatisticsDTO legendStatistics;

	private List<PlayerAchievementDTO> achievements;

	private List<PlayerLabelDTO> labels;

	private List<PlayerTroopDTO> troops;

	private List<PlayerTroopDTO> heroes;

	private List<PlayerTroopDTO> spells;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlayerClanInfoDTO {

		private String tag;

		private String name;

		private Long clanLevel;

		private IconUrlDTO badgeUrls;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlayerLeagueInfoDTO {

		private Long id;

		private String name;

		private IconUrlDTO iconUrls;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlayerAchievementDTO {

		private String name;

		private Long stars;

		private Long value;

		private Long target;

		private String info;

		private String completionInfo;

		private String village;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlayerLabelDTO {

		private Long id;

		private String name;

		private IconUrlDTO iconUrls;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlayerTroopDTO {

		private String name;

		private Long level;

		private Long maxLevel;

		private String village;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlayerLegendStatisticsDTO {

		private Long legendTrophies;

		private PlayerSeasonInfoDTO previousSeason;

		private PlayerSeasonInfoDTO bestSeason;

		private PlayerSeasonInfoDTO currentSeason;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlayerSeasonInfoDTO {

		private String id;

		private Long rank;

		private Long trophies;
	}


}
