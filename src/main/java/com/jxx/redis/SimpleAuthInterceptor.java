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
        if (!"valid".equals(token)) {
            log.info("엑세스 토큰을 재발급합니다. Authorization 헤더를 확인하세요.");
            response.addHeader("Authorization", token);
        }
        return true;
    }
}
