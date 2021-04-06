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
        Assert.assertEquals("@#ZXD@#ABC@" + LocalDate.now().toString(),
            clanWarService.getNormalWarTag(new Date(), "#ZXD", "#ABC"));
        Assert.assertEquals("@#ZXD@#ABC@" + LocalDate.now().toString(),
            clanWarService.getNormalWarTag(new Date(), "#ABC", "#ZXD"));
        Assert.assertEquals("@#5ABC@#2ZXD@" + LocalDate.now().toString(),
            clanWarService.getNormalWarTag(new Date(), "#5ABC", "#2ZXD"));
        Assert.assertEquals("@#5ABC@#2ZXD@" + LocalDate.now().toString(),
            clanWarService.getNormalWarTag(new Date(), "#2ZXD", "#5ABC"));
    }

    @Test
    public void testGetWarSeason() {
        String season = clanWarService.getWarSeason(new Date());
        Assert.assertTrue(LocalDate.now().toString().startsWith(season));
    }
}
