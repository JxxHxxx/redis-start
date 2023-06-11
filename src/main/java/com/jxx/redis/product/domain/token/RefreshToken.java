package com.jxx.redis.product.domain.token;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 3600 * 24 * 7)
public class RefreshToken {

    @Id
    private String memberId;
    private String refreshToken;
    private LocalDateTime createAt;

    public RefreshToken(String memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.createAt = LocalDateTime.now();

    }
}
