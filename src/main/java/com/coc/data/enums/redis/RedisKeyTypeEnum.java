package com.coc.data.enums.redis;

/**
 * @author guokaiqiang
 * @date 2022/12/26 11:21
 */
public enum RedisKeyTypeEnum {
    LOCK("lock"),

    CONCURRENT_LOCK("concurrentLock"),

    NORMAL("normal");

    public final String code;

    RedisKeyTypeEnum(String code) {
        this.code = code;
    }
}
