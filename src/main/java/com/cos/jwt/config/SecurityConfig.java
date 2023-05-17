package com.cos.jwt.config;


import com.cos.jwt.filter.MyFilter3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS )
                .and()
                .formLogin().disable()  //스프링 시큐리티가 제공하는 인증방식인 formlogin 사용 안해
                .httpBasic().disable()  //기본적인 http 로그인 방식 사용안해
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class);//필터 추가 방식 1

                return http.build();
    }
}
