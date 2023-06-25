package com.jxx.redis.ranking.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Getter
@NoArgsConstructor // 역직렬화
public class Rank {
    private String id;
    private String storeName;

    public Rank(String id, String storeName) {
        this.id = id;
        this.storeName = storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank = (Rank) o;
        return Objects.equals(id, rank.id) && Objects.equals(storeName, rank.storeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeName);
    }
}
