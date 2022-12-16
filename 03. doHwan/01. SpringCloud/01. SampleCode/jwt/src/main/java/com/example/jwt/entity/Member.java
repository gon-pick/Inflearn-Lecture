package com.example.jwt.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "member")
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String loginId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String encryptedPwd;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;

    @Builder
    public Member(Long id, String loginId, String password, String encryptedPwd, String name, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.encryptedPwd = encryptedPwd;
        this.name = name;
        this.email = email;
    }
}
