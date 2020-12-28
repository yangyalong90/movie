package com.xxx.common.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    @Bean
    public TokenProvider tokenProvider(){
        return new TokenProvider();
    }

    @Bean
    public JWTTokenHandler tokenHandler(){
        return new JWTTokenHandler();
    }

}
