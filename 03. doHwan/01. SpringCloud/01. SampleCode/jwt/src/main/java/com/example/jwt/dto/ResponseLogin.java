package com.example.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseLogin {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
}
