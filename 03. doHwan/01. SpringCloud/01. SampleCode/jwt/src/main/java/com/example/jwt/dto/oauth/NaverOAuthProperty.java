package com.example.jwt.dto.oauth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NaverOAuthProperty {

    @Value("${oauth.naver.token-url}")
    private String naverTokenUrl;

    @Value("${oauth.naver.grant-type}")
    private String naverGrantType;

    @Value("${oauth.naver.client-id}")
    private String naverClientId;

    @Value("${oauth.naver.client-secret}")
    private String naverClientSecret;

    @Value("${oauth.naver.me-url}")
    private String naverMeUrl;

    public String getNaverTokenUrl(String code, String state) {
        return String.format("%s?grant_type=%s&client_id=%s&client_secret=%s&code=%s&state=%s",
                naverTokenUrl, naverGrantType, naverClientId, naverClientSecret, code, state);
    }
}
