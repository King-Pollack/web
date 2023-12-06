
package com.king.app.common.config;


import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {
    @Value("${cache-redis.host}")
    public String host;
    @Value("${cache-redis.port}")
    public int port;
    @Value("${cache-redis.password}")
    public String password;

    private static final int REDIS_TIMEOUT = 200;

    @Primary
    @Bean(name = "redisCacheTTL10Min")
    public CacheManager redisCacheTTL10Min() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceRedisConnectionFactory());
        RedisCacheConfiguration cacheConfig = getRedisCacheConfiguration(600);
        builder.cacheDefaults(cacheConfig);

        return builder.build();
    }


    @Bean(name = "redisCacheTTL1Hour")
    public CacheManager redisCacheTTL1Hour() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceRedisConnectionFactory());
        RedisCacheConfiguration cacheConfig = getRedisCacheConfiguration(3600);
        builder.cacheDefaults(cacheConfig);
        return builder.build();
    }

    private RedisCacheConfiguration getRedisCacheConfiguration(int seconds) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofSeconds(seconds));
    }

    public RedisConnectionFactory lettuceRedisConnectionFactory() {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(password);

        LettuceClientConfiguration lettuceConf = LettuceClientConfiguration.builder()
                .clientOptions(ClientOptions.builder()
                        .socketOptions(SocketOptions.builder()
                                .connectTimeout(Duration.ofMillis(REDIS_TIMEOUT))
                                .build()
                        ).build()
                ).build();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(config, lettuceConf);
        connectionFactory.afterPropertiesSet();
        connectionFactory.setShareNativeConnection(false);
        return connectionFactory;
    }

    @Bean(name = "redisTemplateCache")
    public RedisTemplate<String, String> redisTemplateConfig() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(lettuceRedisConnectionFactory());
        return redisTemplate;
    }
}
