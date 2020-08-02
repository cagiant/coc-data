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
}
