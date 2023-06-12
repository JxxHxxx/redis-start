package com.jxx.redis.auth.application;

import com.jxx.redis.auth.domain.member.LoginInfo;
import com.jxx.redis.auth.domain.member.Member;
import com.jxx.redis.auth.domain.member.MemberRepository;
import com.jxx.redis.auth.domain.token.RefreshToken;
import com.jxx.redis.auth.domain.token.RefreshTokenRepository;
import com.jxx.redis.auth.dto.LoginForm;
import com.jxx.redis.auth.dto.SignupForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SimpleTokenHandler tokenHandler;
    private final RefreshTokenRepository refreshTokenRepository;

    public void signUp(SignupForm signupForm) {
        if (memberRepository.findByLoginInfoEmail(signupForm.email()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = Member.builder()
                .loginInfo(LoginInfo.of(signupForm.email(), passwordEncoder.encode(signupForm.password())))
                .build();
        memberRepository.save(member);
    }

    public String login(LoginForm loginForm) {
        Member member = memberRepository.findByLoginInfoEmail(loginForm.email()).orElseThrow(
                () -> new IllegalArgumentException("이메일/비밀번호가 잘못되었습니다."));

        member.matches(passwordEncoder.encode(loginForm.password()));
        RefreshToken token = new RefreshToken(member.stringId(), member.email(), UUID.randomUUID().toString());
        log.info("[token id = {}, email = {}]", token.getMemberId(), token.getEmail());
        refreshTokenRepository.save(token);
        return tokenHandler.issue(member);
    }
}
