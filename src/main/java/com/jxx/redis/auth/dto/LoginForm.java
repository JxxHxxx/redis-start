package com.jxx.redis.auth.dto;

public record LoginForm(
        String email,
        String password
) {
}
