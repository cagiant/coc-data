package com.coc.data.service.impl;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author guokaiqiang
 * @date 2021/7/4 21:36
 */
public class MiniProgramMessageServiceImplTest {

	@Test
	public void testSendThreeStarMessage() {
		int minute = LocalDateTime.now().getMinute()/ 5  * 5;
		Assert.assertTrue(LocalDateTime.now().getMinute() > minute);
	}
}
