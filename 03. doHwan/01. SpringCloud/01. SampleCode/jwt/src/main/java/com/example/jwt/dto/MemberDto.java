package com.example.jwt.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MemberDto {
    private Long id;
    private String loginId;
    private String password;
    private String encryptedPwd;
    private String name;
    private String email;

    @Builder
    public MemberDto(Long id, String loginId, String password, String encryptedPwd, String name, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.encryptedPwd = encryptedPwd;
        this.name = name;
        this.email = email;
    }
}
