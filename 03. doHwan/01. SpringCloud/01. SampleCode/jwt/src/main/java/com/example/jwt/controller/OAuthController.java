package com.example.jwt.controller;

import com.example.jwt.dto.ResponseLogin;
import com.example.jwt.dto.oauth.OAuthCallbackParam;
import com.example.jwt.dto.response.Response;
import com.example.jwt.service.OauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class OAuthController {

    private final OauthService oauthService;

    @Autowired
    public OAuthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    /**
     * @param param 네이버 로그인 정보 제공 동의 성공 여부 응답
     * @return
     */
    @GetMapping("/naver")
    public Response authenticateWithNaver(OAuthCallbackParam param) {
        log.info("naver oauth controller : " + param.toString());
        ResponseLogin resp = oauthService.authenticateWithNaver(param);
        return Response.success(resp);
    }
}
