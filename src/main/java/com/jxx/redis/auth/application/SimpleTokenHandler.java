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
        String tokenSecret = tokenInfo.get(1);
        checkSecret(tokenSecret);

        String StringExpired = tokenInfo.get(2);
        
        if (isExpired(StringExpired)) {
            log.info("만료됐음 재발급을 시도합니다.");
            return refreshTokenHandler.reIssue(tokenInfo.get(3));
        }
        return "valid";
    }

    private void checkSecret(String tokenSecret) {
        log.info("[secretKey {}]", secretKey);
        if (!secretKey.equals(tokenSecret)) {
            throw new IllegalArgumentException("조작됨");
        }
    }

    private boolean isExpired(String StringExpired) {
        LocalDateTime expiredTime = LocalDateTime.parse(StringExpired, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        log.info("[expiredTime {}, now {} expired = {}]", expiredTime, LocalDateTime.now(), expiredTime.isBefore(LocalDateTime.now()));

        return expiredTime.isBefore(LocalDateTime.now());

    }
}
