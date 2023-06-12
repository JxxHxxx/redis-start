package com.jxx.redis;

import com.jxx.redis.auth.application.SimpleTokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final SimpleTokenHandler simpleTokenHandler;

    @Bean
    public SimpleAuthInterceptor simpleAuthInterceptor() {
        return new SimpleAuthInterceptor(simpleTokenHandler);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(simpleAuthInterceptor())
                .excludePathPatterns("/auth/**")
                .addPathPatterns("/stores/**");
    }
}
