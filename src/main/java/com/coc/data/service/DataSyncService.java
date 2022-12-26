package com.coc.data.service;

/**
 * @author guokaiqiang
 * @date 2020/7/30 22:01
 */
@Deprecated
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
    void syncClanCurrentWarInfo();

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

    /**
     * 获取联赛对战信息
     * @param
     * @return void
     * @author guokaiqiang
     * @date 2020/9/5 21:32
     **/
    void syncLeagueGroupInfo();

    /**
     * 获取联赛战争信息
     * @param
     * @return void
     * @author guokaiqiang
     * @date 2020/9/6 11:03
     **/
    void syncLeagueGroupWarInfo();
}
