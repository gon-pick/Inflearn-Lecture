package com.example.jwt.service;


import com.example.jwt.dto.ResponseLogin;
import com.example.jwt.dto.oauth.NaverMemberInfoResponse;
import com.example.jwt.dto.oauth.NaverOAuthProperty;
import com.example.jwt.dto.oauth.NaverTokenResponse;
import com.example.jwt.dto.oauth.OAuthCallbackParam;
import com.example.jwt.entity.Member;
import com.example.jwt.repository.MemberRepository;
import com.example.jwt.security.JwtTokenCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class OauthServiceImpl implements OauthService {

    @Value("${admin.token}")
    private String adminToken;
    private final RestTemplate restTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenCreator jwtTokenCreator;
    private final NaverOAuthProperty naverOAuthProperty;

    public OauthServiceImpl(RestTemplate restTemplate, BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository, AuthenticationManager authenticationManager, JwtTokenCreator jwtTokenCreator, NaverOAuthProperty naverOAuthProperty) {
        this.restTemplate = restTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenCreator = jwtTokenCreator;
        this.naverOAuthProperty = naverOAuthProperty;
    }

    @Override
    public ResponseLogin authenticateWithNaver(OAuthCallbackParam param) {
        String tokenUrl = naverOAuthProperty.getNaverTokenUrl(param.getCode(), param.getState());

        // Naver 사용자 정보를 사용하기 위한 AccessToken 요청
        HttpEntity<NaverOAuthProperty> tokenReq = new HttpEntity<>(naverOAuthProperty);
        ResponseEntity<NaverTokenResponse> tokenRespEntity = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenReq, NaverTokenResponse.class);
        NaverTokenResponse tokens = tokenRespEntity.getBody();

        // 응답받은 AccessToken을 사용해 사용자 정보 취득
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokens.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> memberInfoReq = new HttpEntity<>(headers);
        ResponseEntity<NaverMemberInfoResponse> memberInfoResponseEntity = restTemplate.exchange(
                naverOAuthProperty.getNaverMeUrl(), HttpMethod.POST, memberInfoReq, NaverMemberInfoResponse.class
        );
        NaverMemberInfoResponse.Response naverMemberInfo = Objects.requireNonNull(memberInfoResponseEntity.getBody()).getResponse();

        // 회원인지 확인
        String loginId = naverMemberInfo.getId();
        String password = loginId + adminToken;
        Member memberByOauth = memberRepository.findByLoginId(loginId);

        // 등록된 회원이 아닐 경우 회원가입 진행
        if (memberByOauth == null) {
            String encryptedPassword = bCryptPasswordEncoder.encode(password);
            String birth = String.format("%s-%s", naverMemberInfo.getBirthyear(), naverMemberInfo.getBirthday());

            memberByOauth = Member.builder()
                    .loginId(loginId)
                    .password(password)
                    .encryptedPwd(encryptedPassword)
                    .name(naverMemberInfo.getName())
                    .email(naverMemberInfo.getEmail())
                    .build();

            memberRepository.save(memberByOauth);
        }

        // 인증(로그인) 처리
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginId, password));

        Long memberId = memberByOauth.getId();
        String accessToken = jwtTokenCreator.generateAccessToken(memberId);
        String refreshToken = jwtTokenCreator.generateRefreshToken(memberId);

        // 바디에 토큰 및 회원 ID 저장.
        return new ResponseLogin(memberId, accessToken, refreshToken);
    }
}

