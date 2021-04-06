package com.coc.data.task;

import com.coc.data.mapper.ClanMapper;
import com.coc.data.model.base.Clan;
import com.coc.data.model.base.ClanWar;
import com.coc.data.service.ClanService;
import com.coc.data.service.ClanWarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2021/2/11 12:43
 */
@Component
@Slf4j
@RestController
@RequestMapping("/schedule")
public class Schedule {

	/**
	 * mapper
	 **/
	@Resource
	private ClanMapper clanMapper;

	/**
	 * service
	 **/
	@Resource
	private ClanService clanService;
	@Resource
	private ClanWarService clanWarService;

	@Scheduled(cron = "0 */50 * * * *")
	@GetMapping("/clanInfo")
	public void syncClanInfo() {
		List<Clan> clanList = clanMapper.selectByExample(null);
		for (Clan clan : clanList) {
			clanService.syncClanBaseInfo(clan.getTag());
			if (clanService.atLeagueWar(clan.getTag())) {
				clanService.syncLeagueGroupInfo(clan.getTag());
			}
		}
	}

	@Scheduled(cron = "0 */10 * * * *")
	@GetMapping("/currentWarInfo")
	public void syncClanCurrentWarInfo() {
		List<Clan> clanList = clanMapper.selectByExample(null);
		for (Clan clan : clanList) {
			clanWarService.syncClanCurrentWarInfo(clan);
		}
	}
}
