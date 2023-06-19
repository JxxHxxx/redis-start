package com.jxx.redis.auth.domain.token;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 30)
public class RefreshToken {

    @Id
    private String memberId;
    private String email;
    private String refreshToken;
    private LocalDateTime createAt;

    public RefreshToken(String memberId, String email, String refreshToken) {
        this.memberId = memberId;
        this.email = email;
        this.refreshToken = refreshToken;
        this.createAt = LocalDateTime.now();
    }
}
