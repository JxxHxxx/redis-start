package com.jxx.redis.ranking.application;

import com.jxx.redis.ranking.domain.Rank;
import com.jxx.redis.ranking.domain.RankRepository;
import com.jxx.redis.ranking.dto.RankForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RankService {

    private final RankRepository rankRepository;

    public void create(RankForm form) {
        rankRepository.save(new Rank(form.storeId(), form.storeName()));
    }

    public Set<ZSetOperations.TypedTuple<Rank>> getRange(long start, long end) {
        return rankRepository.range(start, end);
    }

    public void addScore(Rank rank) {
        rankRepository.score(rank);
    }

    public void expireRankingBoard() {
        rankRepository.expire();
    }
}
