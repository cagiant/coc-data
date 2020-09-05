package com.coc.data.client;

import com.coc.data.dto.ClanInfoDTO;
import com.coc.data.dto.LeagueGroupInfoDTO;
import com.coc.data.dto.WarInfoDTO;
import org.springframework.web.reactive.function.client.WebClient;

public interface HttpClient {

    ClanInfoDTO getClanInfoByTag(String clanTag);

    WarInfoDTO getClanCurrentWarInfoByClanTag(String clanTag);

    LeagueGroupInfoDTO getClanLeagueGroupInfoByClanTag(String tag);

    WarInfoDTO getClanLeagueGroupWarInfoByTag(String warTag);
}
