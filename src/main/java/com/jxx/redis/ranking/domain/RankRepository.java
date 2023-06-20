package com.jxx.redis.ranking.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RankRepository {

    private static final String STORE_RANK_BOARD_KEY = "storeRank";
    private final RedisTemplate<String, Rank> redisTemplate;

    public void save(Rank rank) {
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("storeRank", rank, 0);
    }

    public Set<ZSetOperations.TypedTuple<Rank>> range(long start, long end) {
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.reverseRangeWithScores(STORE_RANK_BOARD_KEY, start, end);
    }

    public void score(Rank rank) {
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.incrementScore(STORE_RANK_BOARD_KEY, rank, 1);
    }

    public void expire() {
        redisTemplate.expire(STORE_RANK_BOARD_KEY, 0, TimeUnit.SECONDS);
    }
}
