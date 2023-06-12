package com.jxx.redis.auth.domain.token;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByEmail(String email);
    Optional<RefreshToken> findByMemberId(String memberId);

}
