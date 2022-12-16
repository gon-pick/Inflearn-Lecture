package com.example.jwt.controller;

import com.example.jwt.dto.MemberDto;
import com.example.jwt.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity createMember(@RequestBody MemberDto memberDto) {
        log.info("회원 가입 : " + memberDto.toString());
        return ResponseEntity.status(HttpStatus.OK).body(memberService.createMember(memberDto));
    }

    @GetMapping("/healthCheck")
    public ResponseEntity healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body("health-check");
    }
}
