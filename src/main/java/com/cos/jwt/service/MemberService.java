package com.cos.jwt.service;

import com.cos.jwt.Repository.MemberRepository;
import com.cos.jwt.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto findMemberById(Long id){
        return memberRepository.findById(id)
                .map(m->new MemberResponseDto(m.getEmail()))
                .orElseThrow(() -> new RuntimeException("No such user exists"));
    }

    public MemberResponseDto findMemberByEmail(String email){
        return memberRepository.findByEmail(email)
                .map(m->new MemberResponseDto(m.getEmail()))
                .orElseThrow(() -> new RuntimeException("No such user exists"));
    }
}
