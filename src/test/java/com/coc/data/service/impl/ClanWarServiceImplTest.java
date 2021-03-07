package com.coc.data.service.impl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.util.Date;

import static org.mockito.Mockito.*;
/**
 * @author guokaiqiang
 * @date 2021/3/7 14:17
 */
public class ClanWarServiceImplTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Spy
    @InjectMocks
    private ClanWarServiceImpl clanWarService;

    @Test
    public void testGetNormalWarTag() {
        Assert.assertEquals(LocalDate.now().toString(), clanWarService.getNormalWarTag(new Date()));
    }

    @Test
    public void testGetWarSeason() {
        String season = clanWarService.getWarSeason(new Date());
        Assert.assertTrue(LocalDate.now().toString().startsWith(season));
    }
}
