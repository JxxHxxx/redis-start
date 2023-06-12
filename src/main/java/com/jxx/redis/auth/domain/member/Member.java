package com.jxx.redis.auth.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Embedded
    private LoginInfo loginInfo;
    @Builder
    public Member(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public void matches(String password) {
        if (!loginInfo.getPassword().equals(password)) throw new PasswordException();
    }

    public String email() {
        return this.getLoginInfo().getEmail();
    }

    public String stringId() {
        return id.toString();
    }
}
