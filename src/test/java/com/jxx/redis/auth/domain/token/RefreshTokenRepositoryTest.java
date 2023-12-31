package com.jxx.redis.auth.domain.token;

import com.jxx.redis.EmbeddedRedisServerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
@Import(EmbeddedRedisServerConfig.class)
class RefreshTokenRepositoryTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @DisplayName("@RedisHash 테스트")
    @Test
    void name() {
        //given
        String accessCode = UUID.randomUUID().toString();
        RefreshToken token = RefreshToken.builder()
                .refreshToken(accessCode)
                .email("leesin5498@xuni.com")
                .memberId("1")
                .build();
        //when
        refreshTokenRepository.save(token);

        RefreshToken findToken = refreshTokenRepository.findById("1").get();

        assertThat(findToken.getEmail()).isEqualTo("leesin5498@xuni.com");
        assertThat(findToken.getRefreshToken()).isEqualTo(accessCode);
    }
}