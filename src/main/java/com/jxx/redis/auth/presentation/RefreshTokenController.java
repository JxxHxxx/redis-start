package com.jxx.redis.auth.presentation;

import com.jxx.redis.auth.application.RefreshTokenHandler;
import com.jxx.redis.auth.domain.token.RefreshToken;
import com.jxx.redis.auth.domain.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenRepository repository;
    private final RefreshTokenHandler refreshTokenHandler;

    @PostMapping("/token/{member-id}")
    public String issue(@PathVariable("member-id") String memberId) {
        RefreshToken refreshToken = new RefreshToken(memberId, "email", UUID.randomUUID().toString());
        repository.save(refreshToken);
        return "토큰 발급 완료 " + refreshToken.getMemberId();
    }

    @GetMapping("/token/{member-id}")
    public String confirm(@PathVariable("member-id") String memberId) {
        RefreshToken refreshToken = repository.findById(memberId).get();
        return "토큰 확인 " + refreshToken.getRefreshToken();
    }

    @GetMapping("ref/{member-id}")
    public String check(@PathVariable("member-id") String memberId) {
        String token = refreshTokenHandler.reIssue(memberId);
        return "토큰 재발급 " + token;
    }
}
