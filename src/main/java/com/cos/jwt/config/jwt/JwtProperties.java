package com.cos.jwt.config.jwt;

public interface JwtProperties {
    String secret = "dohyun";
    int EXPIRATION_TIME = 60000*10; // 10mins
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
