package com.xxx.common.security.jwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    @Bean
    public JWTTokenProvider tokenProvider(){
        return new JWTTokenProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public JWTTokenHandler tokenHandler(){
        return new JWTTokenHandler();
    }

}
