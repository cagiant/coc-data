package com.coc.data.service;

/**
 * @author guokaiqiang
 * @date 2020/7/30 22:01
 */
public interface DataSyncService {

    /**
     * 同步部落基本信息
     * @param
     * @return void
     * @author guokaiqiang
     * @date 2020/7/30 22:01
     **/
    void syncClanInfo();

    /**
     * 同步部落的战争信息
     * @param
     * @return void
     * @author guokaiqiang
     * @date 2020/8/1 16:52
     **/
    void syncChanCurrentWarInfo();

    /**
     * 计算部落战日志，将对应的星星加给成员。
     * @param  
     * @return void
     * @author guokaiqiang
     * @date 2020/8/8 09:08
     **/
    void calculateClanWarLogs();

    /**
     * 计算当前赛季的报表
     * @param
     * @return void
     * @author guokaiqiang
     * @date 2020/8/11 22:36
     **/
    void generateSeasonReports();
}
