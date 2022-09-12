package com.coc.data.enums.redis;

/**
 * @author guokaiqiang
 * @date 2022/12/26 11:10
 */
public enum RedisKeyEnum {

    CLAN("clan");

    private final String prefix;
    RedisKeyEnum(String prefix) {
        this.prefix = prefix;
    }

    public String buildKey(String key, Object... param) {
        return this.prefix + ":" + String.format(key, param);
    }
}
