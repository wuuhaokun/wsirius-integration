package com.wsirius.core.redis;

import java.util.List;

import com.wsirius.core.redis.connection.CustomJedisConnectionConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * 动态 RedisTemplate ，以支持动态切换 redis database
 *
 * @author bojiangzhou 2018/08/28
 */
public class DynamicRedisTemplate<K, V> extends AbstractRoutingRedisTemplate<K, V> {

    private RedisProperties properties;
    private ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration;
    private ObjectProvider<RedisClusterConfiguration> clusterConfiguration;
    private ObjectProvider<List<JedisClientConfigurationBuilderCustomizer>> builderCustomizer;

    public DynamicRedisTemplate(RedisProperties properties,
                       ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration,
                       ObjectProvider<RedisClusterConfiguration> clusterConfiguration,
                       ObjectProvider<List<JedisClientConfigurationBuilderCustomizer>> builderCustomizer) {
        this.properties = properties;
        this.sentinelConfiguration = sentinelConfiguration;
        this.clusterConfiguration = clusterConfiguration;
        this.builderCustomizer = builderCustomizer;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return RedisDatabaseThreadLocal.get();
    }

    @Override
    protected RedisTemplate<K, V> createRedisTemplateOnMissing(Object lookupKey) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setStringSerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory((Integer) lookupKey));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    public JedisConnectionFactory redisConnectionFactory(int database) {
        CustomJedisConnectionConfiguration jedisConnectionConfiguration = new CustomJedisConnectionConfiguration(properties,
                sentinelConfiguration, clusterConfiguration, builderCustomizer);
        return jedisConnectionConfiguration.redisConnectionFactory(database);
    }

}
