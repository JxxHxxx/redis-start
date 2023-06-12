package com.jxx.redis.auth.dto;

public record SignupForm(
        String email,
        String password
) {
}
