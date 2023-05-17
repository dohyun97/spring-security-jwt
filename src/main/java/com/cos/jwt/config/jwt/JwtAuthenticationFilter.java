package com.cos.jwt.config.jwt;

import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.domain.User;
import com.cos.jwt.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있어
 * /login 요청해서 username,password 전송하면 (POST) UsernamePasswordAuthenticationFilter 가 동작
 * 지금은 formLogin을 disable해서 UsernamePasswordAuthenticationFilter가 작동 하도록 security config에 등록
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login을 요청하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //1. username,password를 받아서 정상인지 로그인 시도.
        //2. authenticationManager롤 로그인 시도를 하면 PrincipalDetailsService가 호출 되서 loadByUsername() 호출.
        //3. 여기서 PrincipalDetails return. principalDetails를 세션에 담는 이유가 권한관리를 위해서
        //4. JWT 토큰을 만들어서 응답해주면 돼.
        log.info("Using JWTAuthentication Filter");

        ObjectMapper om = new ObjectMapper();  //Json type req
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class); //LoginRequestDto 에 정보를 다 담아서 객체를 반환
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("JwtAuthenticationFilter : {}",loginRequestDto);
        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword());
        log.info("AuthenticationToken : {}",authenticationToken);
        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        log.info("Auth : {}",authentication);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("Authentication : {} , successfully Login",principalDetails.getUser().getUsername()); //이게 나오면 로그인이 되었다는 뜻
        return authentication;

    }

    //attemptAuthentication실행 후 인증이 정상적으로 처리되었으면 successfulAuthentication함수 실행
    //JWT token을 여기서 만들어서 요청한 사용자에게 토큰을 응답.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Process successfulAuthentication(): It means successfully authenticated");
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
