package com.coc.data.enums.redis;

/**
 * @author guokaiqiang
 * @date 2022/12/26 13:36
 */
public enum WarRedisEnum implements RedisDetailKeyBuilder{

    CLAN_WAR_SYNC("CLAN_WAR_SYNC:WAR_TAG:%s", RedisKeyTypeEnum.CONCURRENT_LOCK),
    ;

    public final String key;

    public final RedisKeyTypeEnum keyType;

    WarRedisEnum(String key, RedisKeyTypeEnum keyType) {
        this.key = key;
        this.keyType = keyType;
    }

    @Override
    public String build() {
        return key + ":" + keyType.code;
    }
}
