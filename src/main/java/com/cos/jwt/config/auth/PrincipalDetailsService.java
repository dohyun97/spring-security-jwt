package com.cos.jwt.config.auth;

import com.cos.jwt.Repository.MemberRepository;
import com.cos.jwt.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//localhost:8080/login 일 때 이 클래스가 작동
//근데 여기서는 404 에러떠. SecurityConfig에서 formLogin().disable을 했기 때문에
@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        User user = memberRepository.findByUsername(username);
        return new PrincipalDetails(user);
    }
}
