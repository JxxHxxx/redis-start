package com.jxx.redis.auth.domain.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginInfo {
    private String email;
    private String password;

    private LoginInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginInfo of(String email, String password) {
        return new LoginInfo(email, password);
    }
}
