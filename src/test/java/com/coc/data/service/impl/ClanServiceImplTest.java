package com.coc.data.service.impl;

import com.coc.data.client.CocApiHttpClient;
import com.coc.data.dto.ClanInfoDTO;
import com.coc.data.mapper.ClanMapper;
import com.coc.data.service.PlayerService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;
/**
 * @author guokaiqiang
 * @date 2021/3/7 09:59
 */
public class ClanServiceImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Spy
    @InjectMocks
    private ClanServiceImpl clanService;
    @Mock
    private CocApiHttpClient httpClient;
    @Mock
    private ClanMapper clanMapper;
    @Mock
    private PlayerService playerService;

    @Test
    public void testGetClanInfo() {
        when(httpClient.getClanInfoByTag(any())).thenReturn(new ClanInfoDTO());
        Assert.assertNotNull(clanService.getClanInfo(""));
    }
}
