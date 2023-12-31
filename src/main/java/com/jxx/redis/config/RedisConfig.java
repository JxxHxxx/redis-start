package com.jxx.redis.config;

import ch.qos.logback.classic.spi.LogbackServiceProvider;
import com.jxx.redis.ranking.domain.Rank;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.SLF4JServiceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collections;

@Slf4j
@Configuration
@EnableRedisRepositories(keyspaceConfiguration = RedisConfig.MyKeyspaceConfiguration.class)
public class RedisConfig {

    @Value("${spring.data.redis.port}")
    public int port;
    @Value("${spring.data.redis.host}")
    public String host;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        log.info("[DEV & PROD REDIS SETTING START ]");
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Rank.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Rank.class));

        return redisTemplate;
    }

    public static class MyKeyspaceConfiguration extends KeyspaceConfiguration {

        @Override
        protected Iterable<KeyspaceSettings> initialConfiguration() {
            KeyspaceSettings keyspaceSettings = new KeyspaceSettings(Rank.class, "rank");
            keyspaceSettings.setTimeToLive(10L);
            return Collections.singleton(keyspaceSettings);
        }
    }
}
