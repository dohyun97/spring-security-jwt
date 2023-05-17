package com.cos.jwt.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyFilter3 extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Filter 3");

        if(request.getMethod().equals("POST")){
            log.info("POST request");
            String headerAuth = request.getHeader("Authorization"); //헤더 authorizaiton 값 가져와
            //이 필터에서 토큰(헤더 authorizaiton 값)이 cos 일때만 controller로 진입하게 하고 그게아니면 진입 못하게 막아
            if(headerAuth.equals("cos")){
                filterChain.doFilter(request,response); //계속 진행시키기 위해 이걸 넣어줘
            }else {
                PrintWriter out = response.getWriter();
                out.println("No Auth");
            }
        }

    }

}
