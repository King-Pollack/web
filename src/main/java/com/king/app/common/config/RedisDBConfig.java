package com.king.app.common.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisDBConfig {
    @Value("${db-redis.host}")
    public String host;
    @Value("${db-redis.port}")
    public int port;
    @Value("${db-redis.password}")
    public String password;

    private static final int REDIS_TIMEOUT = 200;

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

    @Bean(name = "redisTemplateDB")
    public RedisTemplate<String, String> redisTemplateConfig() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(lettuceRedisConnectionFactory());
        return redisTemplate;
    }
}
