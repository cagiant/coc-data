package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.dto.ClanInfoDTO;
import com.coc.data.mapper.ClanMapper;
import com.coc.data.model.Clan;
import com.coc.data.service.ClanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author guokaiqiang
 * @date 2021/2/11 11:30
 */
@Service
@Slf4j
public class ClanServiceImpl implements ClanService {

    @Resource
    private CocApiHttpClient httpClient;
    @Resource
    private ClanMapper clanMapper;

    @Override
    public ClanInfoDTO getClanInfo(String clanTag) {
        return httpClient.getClanInfoByTag(clanTag);
    }

    @Override
    public void syncClanBaseInfo(String clanTag) {
        ClanInfoDTO clanInfo = getClanInfo(clanTag);
        Clan clan = Clan.builder()
            .name(clanInfo.getName())
            .tag(clanTag)
            .build();
        clanMapper.insertOnDuplicateKeyUpdate(clan);
    }
}
