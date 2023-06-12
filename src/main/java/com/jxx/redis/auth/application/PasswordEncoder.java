package com.jxx.redis.auth.application;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoder {

    private final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

    public PasswordEncoder() throws NoSuchAlgorithmException {
    }

    public String encode(String password) {
        byte[] hash = messageDigest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
