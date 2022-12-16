package com.example.jwt.security;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* JWT 토큰 적합성 확인 하는 로직 */
@Component
@Slf4j
public class BearerAuthInterceptor implements HandlerInterceptor {
    private AuthorizationExtractor authExtractor;
    private JwtTokenUtil jwtTokenUtil;
    @Value("${token.member.secret}")
    private String secret;

    public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtTokenUtil jwtTokenUtil) {
        this.authExtractor = authExtractor;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        log.info(">>> interceptor.preHandle 호출 <<<");

        String accessToken = authExtractor.extract(request, "Bearer");
        // Request Header 에 Access Token (Authorization) 이 담긴 경우
        if (!Strings.isEmpty(accessToken)) {
            // Access Token 이 만료된 경우
            if(jwtTokenUtil.isTokenExpired(accessToken, secret)) {
                throw new JwtException("토큰 만료");
            }

            if (jwtTokenUtil.isInvalidToken(accessToken, secret)) {
                throw new JwtException("유효하지 않은 토큰");
            }
        }else {
            throw new JwtException("토큰 존재 여부 확인");
        }

        log.info(accessToken);
        return true;
    }
}
