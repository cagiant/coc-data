package com.coc.data.client;

import com.coc.data.dto.ClanInfoDTO;
import com.coc.data.dto.LeagueGroupInfoDTO;
import com.coc.data.dto.PlayerDTO;
import com.coc.data.dto.WarInfoDTO;

public interface HttpClient {

    ClanInfoDTO getClanInfoByTag(String clanTag);

    WarInfoDTO getClanCurrentWarInfoByClanTag(String clanTag);

    LeagueGroupInfoDTO getClanLeagueGroupInfoByClanTag(String tag);

    WarInfoDTO getClanLeagueGroupWarInfoByTag(String warTag);

    PlayerDTO getPlayerInfoByTag(String playerTag);
}
