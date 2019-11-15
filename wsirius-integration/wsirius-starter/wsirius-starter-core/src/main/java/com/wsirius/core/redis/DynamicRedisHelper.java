package com.wsirius.core.redis;

import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 支持动态切换Redis database
 *
 * @author bojiangzhou 2018-02-12
 */
public class DynamicRedisHelper extends RedisHelper {

    public DynamicRedisHelper(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate, objectMapper);
    }

    /**
     * 更改当前线程 RedisTemplate database，调用该方法后，需要调用 {@link DynamicRedisHelper#clearCurrentDatabase}
     * 方法清除已设置的 database.
     * 
     * @param database set current redis database
     */
    @Override
    public void setCurrentDatabase(int database) {
        RedisDatabaseThreadLocal.set(database);
    }

    @Override
    public void clearCurrentDatabase() {
        RedisDatabaseThreadLocal.clear();
    }

}
