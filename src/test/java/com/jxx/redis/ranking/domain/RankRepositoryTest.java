package com.jxx.redis.ranking.domain;

import com.jxx.redis.EmbeddedRedisServerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataRedisTest
@Import(EmbeddedRedisServerConfig.class)
class RankRepositoryTest {

    @Autowired
    RedisTemplate redisTemplate;

    @DisplayName("redisTemplate 테스트")
    @Test
    void test() {
        ZSetOperations<String, Rank> ranks = redisTemplate.opsForZSet();
        Rank rank = new Rank("1", "마라탕");
        ranks.add("rankingBoard", rank, 0);

        ZSetOperations<String, Rank> updatedRanks = redisTemplate.opsForZSet();
        Set<Rank> rankingBoard = updatedRanks.range("rankingBoard", 0, 10);

        assertThat(rankingBoard.contains(rank)).isTrue();
    }
}