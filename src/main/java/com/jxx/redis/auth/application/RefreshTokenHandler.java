package com.jxx.redis.auth.application;

import com.jxx.redis.auth.domain.token.RefreshToken;
import com.jxx.redis.auth.domain.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenHandler {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${token.secret.key}")
    private String secretKey;

    public String reIssue(String id) {
        Optional<RefreshToken> oRefreshToken = refreshTokenRepository.findById(id);
        if (oRefreshToken.isPresent()) {
            RefreshToken refreshToken = oRefreshToken.get();
            return refreshToken.getEmail() + ">" + secretKey + ">" + LocalDateTime.now().plusSeconds(60) + ">" + refreshToken.getMemberId();
        }

        throw new IllegalArgumentException("로그인이 필요합니다.");
    }
}
