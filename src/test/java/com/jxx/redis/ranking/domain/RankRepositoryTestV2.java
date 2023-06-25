package com.jxx.redis.ranking.domain;

import com.jxx.redis.EmbeddedRedisServerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
@Import({EmbeddedRedisServerConfig.class, RedisCommon.class})
public class RankRepositoryTestV2 {

    @Autowired
    RankRepository rankRepository;

    @DisplayName("redisTemplate 을 의존하는 repository Bean으로 테스트")
    @Test
    void create_rank() {
        Rank rank = new Rank("1", "엑스유니마라탕");
        rankRepository.save(rank);

        Set<ZSetOperations.TypedTuple<Rank>> zSetRanks = rankRepository.range(0, -1);

        List<Rank> ranks = zSetRanks.stream().map(r -> r.getValue()).toList();
        Rank savedRank = ranks.get(0);

        assertThat(savedRank.getStoreName()).isEqualTo("엑스유니마라탕");
    }
}
