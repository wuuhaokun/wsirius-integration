package com.wsirius.core.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RedisProperties
 */
@ConfigurationProperties(prefix = SunnyRedisProperties.PREFIX)
public class SunnyRedisProperties {

    public static final String PREFIX = "sunny.redis";

    /**
     * 是否开启动态数据库切换
     */
    private boolean dynamicDatabase = false;

    public boolean isDynamicDatabase() {
        return dynamicDatabase;
    }

    public void setDynamicDatabase(boolean dynamicDatabase) {
        this.dynamicDatabase = dynamicDatabase;
    }
}
