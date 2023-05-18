package com.cos.jwt.controller;

import com.cos.jwt.Repository.MemberRepository;
import com.cos.jwt.domain.Role;
import com.cos.jwt.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class RestApiController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    // 모든 사람이 접근 가능
    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }
   //To test Token
    @PostMapping("token")
    public String token() {
        return "<h1>token</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody User user){
     log.info("User : {}",user.getUsername());
      user.setUsername(user.getUsername());
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      user.setRole(Role.ROLE_USER);
      memberRepository.save(user);
      return "Successfully Signup!";
    }

    @GetMapping("user")
    public String user(){
        return "user";
    }

    @GetMapping("manager")
    public String manager(){
        return "manager";
    }

    @GetMapping("admin")
    public String admin(){
        return "admin";
    }

}
