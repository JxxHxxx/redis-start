package com.jxx.redis.ranking.presentation;

import com.jxx.redis.ranking.application.RankService;
import com.jxx.redis.ranking.domain.Rank;
import com.jxx.redis.ranking.dto.RankForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @PostMapping("/ranks")
    public void createRank(@RequestBody RankForm form) {
        rankService.create(form);
        log.info("저장 완료");
    }

    @GetMapping("/ranks")
    public ResponseEntity<Set<ZSetOperations.TypedTuple<Rank>>> getRange(@RequestParam(defaultValue = "0") long start, @RequestParam(defaultValue = "100") long end) {
        Set<ZSetOperations.TypedTuple<Rank>> ranks = rankService.getRange(start, end);

        return ResponseEntity.ok(ranks);
    }

    @GetMapping("/ranks/add-score")
    public String getRange(@RequestBody Rank rank) {
        rankService.addScore(rank);

        return "스코어 반영";
    }
}
