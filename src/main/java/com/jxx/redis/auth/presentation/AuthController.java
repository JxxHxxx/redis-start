package com.jxx.redis.auth.presentation;

import com.jxx.redis.auth.application.AuthService;
import com.jxx.redis.auth.dto.LoginForm;
import com.jxx.redis.auth.dto.SignupForm;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/sign-up")
    public String signUp(@RequestBody SignupForm signupForm) {
        authService.signUp(signupForm);
        return "가입 완료";
    }
    @PostMapping("/auth/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        String token = authService.login(loginForm);
        response.addHeader("Authorization", token);
        return token;
    }

}
