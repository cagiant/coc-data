package com.coc.data.controller;

import com.coc.data.controller.request.war.WarDetailGetRequest;
import com.coc.data.controller.vo.war.WarDetailVO;
import com.coc.data.service.ClanWarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author guokaiqiang
 * @date 2021/7/10 21:53
 */
@RestController
@RequestMapping("/war")
public class WarController {

	@Resource
	private ClanWarService clanWarService;

	@GetMapping("/detail")
	public WarDetailVO getWarDetail(@Valid WarDetailGetRequest warDetailGetRequest) {
		String warTag = warDetailGetRequest.getWarTag();
		String clanTag = warDetailGetRequest.getClanTag();

		return clanWarService.getWarDetail(warTag, clanTag);
	}
}
