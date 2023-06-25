package com.jxx.redis.ranking.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RankRepository {

    private static final String STORE_RANK_BOARD_KEY = "storeRank";
    private final RedisTemplate<String, Rank> redisTemplate;

    public void save(Rank rank) {
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(STORE_RANK_BOARD_KEY, rank, 0);
//        zSetOperations.addIfAbsent(STORE_RANK_BOARD_KEY, rank, 0);
    }

    public Set<ZSetOperations.TypedTuple<Rank>> range(long start, long end) {
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.reverseRangeWithScores(STORE_RANK_BOARD_KEY, start, end);
    }

    /**
     * addIfAbsent -> return 해당 키가 존재하지 않을 경우(아래 메서드에선 rank) 키를 sorted-set에 추가하고 true를 반환한다.
     * 만약 이미 키가 존재하다변 false를 반호나한다.
     */

    public void score(Rank rank) {
        ZSetOperations<String, Rank> zSetOperations = redisTemplate.opsForZSet();
        Boolean notExistedRankKey = zSetOperations.addIfAbsent(STORE_RANK_BOARD_KEY, rank, 0);

        if (notExistedRankKey) {
            throw new IllegalArgumentException("존재하지 않는 Rank 키 입니다.");
        }

        zSetOperations.incrementScore(STORE_RANK_BOARD_KEY, rank, 1);
    }

    public void expire() {
        redisTemplate.expire(STORE_RANK_BOARD_KEY, 0, TimeUnit.SECONDS);
    }
}
