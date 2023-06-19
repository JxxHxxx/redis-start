package com.jxx.redis.ranking.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor // 역직렬화
public class Rank {
    private String id;
    private String storeName;

    public Rank(String id, String storeName) {
        this.id = id;
        this.storeName = storeName;
    }
}
