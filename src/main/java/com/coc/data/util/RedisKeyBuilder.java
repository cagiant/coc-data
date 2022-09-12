package com.coc.data.util;

/**
 * @author guokaiqiang
 * @date 2021/7/8 22:00
 */
public class RedisKeyBuilder {


	public static String getMiniprogramToken() {
		return "MINI_TOKEN";
	}

	@Deprecated
	public static String buildClanInfoKey(String clanTag) {
		return String.format("CLAN_INFO:%s", clanTag);
	}

	public static String buildThreeStarWarTagKey(String warTag) {
		return String.format("THREE_STAR_WAR_INFO:%s",warTag);
	}
}
