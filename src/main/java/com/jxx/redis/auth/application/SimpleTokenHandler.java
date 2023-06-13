package com.jxx.redis.auth.application;

import com.jxx.redis.auth.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/** 토이 프로젝트 용 인증 토큰 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleTokenHandler {

    private final RefreshTokenHandler refreshTokenHandler;

    @Value("${token.secret.key}")
    private String secretKey;

    public String issue(Member member) {
        return member.email() + ">" + secretKey + ">" + LocalDateTime.now().plusSeconds(60) + ">" + member.getId();
    }

    public String validate(String token) {
        List<String> tokenInfo = Arrays.stream(token.split(">")).toList();

        String secret = tokenInfo.get(1);
        checkSecret(secret);

        String expiredTime = tokenInfo.get(2);
        if (isExpired(expiredTime)) {
            log.info("[리프레시 토큰을 통해 재발급을 시도합니다.]");
            return refreshTokenHandler.reIssue(tokenInfo.get(3));
        }

        return "valid";
    }

    private void checkSecret(String tokenSecret) {
        log.info("[Token의 SecretKey 값을 검증합니다.]");
        if (!secretKey.equals(tokenSecret)) {
            throw new IllegalArgumentException("유효하지 않은 SecretKey");
        }
    }

    private boolean isExpired(String StringExpired) {
        LocalDateTime expiredTime = LocalDateTime.parse(StringExpired, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        boolean isExpired = expiredTime.isBefore(LocalDateTime.now());
        log.info("[토큰의 만료 시간을 검사합니다. expiredTime {}, now {} expired = {}]", expiredTime, LocalDateTime.now(), isExpired);

        if (isExpired) {
            log.info("[만료된 엑세스 토큰입니다.]");
        }

        if (!isExpired) {
            log.info("[만료되지 않은 엑세스 토큰입니다.]");
        }
        return isExpired;
    }
}
