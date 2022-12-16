package com.example.jwt.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* MA에서 인터셉터 등록을 통한 인가 과정 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    private final BearerAuthInterceptor bearerAuthInterceptor;

    public WebMvcConfig(BearerAuthInterceptor bearerAuthInterceptor) {
        this.bearerAuthInterceptor = bearerAuthInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry){
        log.info(">>> 인터셉터 등록 <<<");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/member/healthCheck");
    }
}
