package com.coc.data.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author guokaiqiang
 * @date 2021/7/8 21:56
 */
@Component
@Slf4j
public class RedisUtil {
	private static final Integer LOG_TIME_OUT = 3600 * 12;
	private static boolean inited = false;
	private StringRedisTemplate redisTemplate;

	public RedisUtil(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
		try {
			redisTemplate.opsForValue().set("test", "test", 10, TimeUnit.MILLISECONDS);
			inited = true;
			log.info("redis初始化成功");
		} catch (Exception e) {
			log.error("redis初始化失败，请检查配置");
		}
	}

	public boolean isInited() {
		return inited;
	}

	public void set(String key, String value) {
		if (!inited) {
			return;
		}
		try {
			redisTemplate.opsForValue().set(key, value, LOG_TIME_OUT, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void setex(String key, String value, int seconds) {
		if (!inited) {
			return;
		}
		try {
			redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public boolean setnxWithMilliseconds(String key, String value, int milliseconds) {
		if (!inited) {
			return false;
		}
		try {
			Boolean result =  redisTemplate.opsForValue().setIfAbsent(key, value, milliseconds,
				TimeUnit.MILLISECONDS);
			return result != null && result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	public String get(String key) {
		if (!inited) {
			return null;
		}
		if (Objects.isNull(key)) {
			return null;
		}
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public Boolean delete(String key) {
		if (!inited) {
			return false;
		}
		if (key == null) {
			return false;
		}
		try {
			return redisTemplate.delete(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	public void hset(String key, Map<String, Object> valueMap, long seconds) {
		if (!inited) {
			return;
		}
		Assert.notNull(key, "key不能为空");
		BoundHashOperations<String, String, Object> hashOperations = redisTemplate.boundHashOps(key);
		hashOperations.putAll(valueMap);
		hashOperations.expire(seconds, TimeUnit.SECONDS);
	}

	public Object hget(String key, String hashKey) {
		if (!inited) {
			return null;
		}
		Assert.notNull(key, "key不能为空");
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	public void leftPush(String key, String value) {
		if (!inited) {
			return;
		}
		if (key != null && value != null) {
			redisTemplate.opsForList().leftPush(key, value);
			log.info("lpush {}, value：{}", key, value);
		}
	}

	public void leftPushAll(String key, Collection<String> values) {
		if (!inited) {
			return;
		}
		if (key != null && values != null && !values.isEmpty()) {
			redisTemplate.opsForList().leftPushAll(key, values);
			log.info("lpush {}, values：{}", key, values);
		}
	}

	public String rightPop(String key) {
		if (!inited) {
			return null;
		}
		Assert.notNull(key, "key不能为空");
		return redisTemplate.opsForList().rightPop(key);
	}

	public String rightPopAndLeftPush(String sourceKey, String destKey) {
		if (!inited) {
			return null;
		}
		Assert.notNull(sourceKey, "源key不能为空");
		Assert.notNull(destKey, "目标key不能为空");

		return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destKey, Duration.ofSeconds(1));
	}

	public String leftPop(String key) {
		if (!inited) {
			return null;
		}
		Assert.notNull(key, "key不能为空");
		return redisTemplate.opsForList().leftPop(key);
	}
}
