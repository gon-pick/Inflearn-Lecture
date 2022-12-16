package com.example.jwt.service;

import com.example.jwt.dto.ResponseLogin;
import com.example.jwt.dto.oauth.OAuthCallbackParam;

public interface OauthService {
    ResponseLogin authenticateWithNaver(OAuthCallbackParam param);
}
