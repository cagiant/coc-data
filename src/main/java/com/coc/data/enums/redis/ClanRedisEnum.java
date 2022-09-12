package com.coc.data.enums.redis;

/**
 * @author guokaiqiang
 * @date 2022/12/26 11:14
 */
public enum ClanRedisEnum implements RedisDetailKeyBuilder{

    CLAN_INFO("CLAN_INFO:%s", RedisKeyTypeEnum.CONCURRENT_LOCK),
    ;

    public final String key;

    public final RedisKeyTypeEnum keyType;

    ClanRedisEnum(String key, RedisKeyTypeEnum keyType) {
        this.key = key;
        this.keyType = keyType;
    }

    @Override
    public String build() {
        return key + ":" + keyType.code;
    }
}
