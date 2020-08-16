package com.coc.data.controller;

import com.coc.data.service.DataSyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("")
public class TestController {

    @Resource
    private DataSyncService dataSyncService;

    @GetMapping("/test")
    public void test() {
        dataSyncService.syncClanInfo();
        dataSyncService.syncClanCurrentWarInfo();
        dataSyncService.calculateClanWarLogs();
        dataSyncService.generateSeasonReports();
    }
}
