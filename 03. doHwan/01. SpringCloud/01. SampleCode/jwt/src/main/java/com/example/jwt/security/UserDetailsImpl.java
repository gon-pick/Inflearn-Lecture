package com.example.jwt.security;

import com.example.jwt.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }


    public Long getId() {
        return member.getId();
    }

    @Override
    public String getPassword() {
        return member.getEncryptedPwd();
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
