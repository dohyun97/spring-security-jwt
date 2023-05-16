package com.cos.jwt.controller;

import com.cos.jwt.dto.MemberResponseDto;
import com.cos.jwt.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponseDto findByEmail(@PathVariable String email){
        return memberService.findMemberByEmail(email);
    }
}
