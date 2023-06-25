package com.jxx.redis.ranking.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@TestConfiguration
public class RedisCommon {

    @Autowired
    RedisTemplate redisTemplate;

    @Bean
    RankRepository rankRepository() {
        return new RankRepository(redisTemplate);
    }
}
