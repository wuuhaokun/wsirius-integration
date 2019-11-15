package com.wsirius.core.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Redis配置类
 */
@Configuration
@EnableConfigurationProperties(SunnyRedisProperties.class)
public class SunnyRedisAutoConfiguration {

    /**
     * 覆盖默认配置 RedisTemplate，使用 String 类型作为key，设置key/value的序列化规则
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory,
                    Jackson2ObjectMapperBuilder builder) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 使用 Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(String.class);
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory,
                    RedisTemplate<String, String> redisTemplate) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        template.setKeySerializer(redisTemplate.getKeySerializer());
        template.setValueSerializer(redisTemplate.getValueSerializer());
        template.setHashKeySerializer(redisTemplate.getHashKeySerializer());
        template.setHashValueSerializer(redisTemplate.getHashValueSerializer());

        return template;
    }

    @Bean
    @ConditionalOnProperty(prefix = SunnyRedisProperties.PREFIX, name = "dynamic-database", havingValue = "false",
                    matchIfMissing = true)
    public RedisHelper redisHelper(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        return new RedisHelper(redisTemplate, objectMapper);
    }


    /**
     * 动态 RedisHelper 配置
     */
    @Configuration
    @ConditionalOnProperty(prefix = SunnyRedisProperties.PREFIX, name = "dynamic-database", havingValue = "true", matchIfMissing = false)
    protected static class DynamicRedisAutoConfiguration {

        @Bean
        @SuppressWarnings("unchecked")
        public DynamicRedisTemplate<String, String> dynamicRedisTemplate(RedisTemplate<String, String> redisTemplate,
                        RedisProperties properties,
                        ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration,
                        ObjectProvider<RedisClusterConfiguration> clusterConfiguration,
                        ObjectProvider<List<JedisClientConfigurationBuilderCustomizer>> builderCustomizer) {
            DynamicRedisTemplate<String, String> dynamicRedisTemplate = new DynamicRedisTemplate(properties,
                            sentinelConfiguration, clusterConfiguration, builderCustomizer);
            dynamicRedisTemplate.setDefaultRedisTemplate(redisTemplate);
            Map<Object, RedisTemplate<String, String>> map = new HashMap<>();
            map.put(properties.getDatabase(), redisTemplate);
            dynamicRedisTemplate.setRedisTemplates(map);
            return dynamicRedisTemplate;
        }

        @Bean
        public RedisHelper redisHelper(DynamicRedisTemplate<String, String> dynamicRedisTemplate, ObjectMapper objectMapper) {
            return new DynamicRedisHelper(dynamicRedisTemplate, objectMapper);
        }

    }



}
