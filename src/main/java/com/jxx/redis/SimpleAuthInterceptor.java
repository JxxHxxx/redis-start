package com.jxx.redis;


import com.jxx.redis.auth.application.SimpleTokenHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class SimpleAuthInterceptor implements HandlerInterceptor {

    private final SimpleTokenHandler simpleTokenHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = simpleTokenHandler.validate(request.getHeader("Authorization"));
        log.info("[인터셉터 동작 token = {}]", token);
        if (!"valid".equals(token)) {
            log.info("재발급 토큰을 헤더에 추가합니다.");
            response.addHeader("Authorization", token);
        }
        return true;
    }
}
