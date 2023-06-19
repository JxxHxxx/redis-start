package com.jxx.redis.ranking.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RankRepository {

    private final RedisTemplate<String, Rank> redisTemplate;

    public void save(Rank rank) {
        log.info("저장 합니다. " + rank.getId());
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("storeRank", rank, 0);
    }

    public Set<ZSetOperations.TypedTuple<Rank>> range(long start, long end) {
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();

        return zSetOperations.reverseRangeWithScores("storeRank", start, end);
    }

    public void score(Rank rank) {
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.incrementScore("storeRank", rank, 1);
    }
}
