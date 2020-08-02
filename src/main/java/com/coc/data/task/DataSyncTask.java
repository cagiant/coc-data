package com.coc.data.task;

import com.coc.data.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author guokaiqiang
 * @date 2020/7/30 21:20
 */
@Component
@Slf4j
public class DataSyncTask {

    @Resource
    private DataSyncService dataSyncService;

//    @Scheduled(cron = "* 0 */1 * * *")
    public void syncClanInfo() {
        dataSyncService.syncClanInfo();
    }

    @Scheduled(cron = "0 40 23 * * *")
    public void syncClanWarInfo() {
        dataSyncService.syncChanCurrentWarInfo();
    }
}
