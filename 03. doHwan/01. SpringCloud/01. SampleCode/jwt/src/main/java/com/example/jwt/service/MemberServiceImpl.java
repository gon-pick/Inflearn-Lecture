package com.example.jwt.service;

import com.example.jwt.dto.MemberDto;
import com.example.jwt.entity.Member;
import com.example.jwt.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Transactional(readOnly = true)
@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

    MemberRepository memberRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public MemberDto createMember(@RequestBody MemberDto memberDto) {
        memberDto.setEncryptedPwd(passwordEncoder.encode(memberDto.getPassword()));
        Member member = Member.builder()
                .loginId(memberDto.getLoginId())
                .password(memberDto.getPassword())
                .encryptedPwd(memberDto.getEncryptedPwd())
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .build();

        memberRepository.save(member);
        log.info("member : " + member.toString());
        return memberDto;
    }
}
