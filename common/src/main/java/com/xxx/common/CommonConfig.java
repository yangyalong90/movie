package com.xxx.common;

import com.xxx.common.rest.RestConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public RestConfig restConfig(){
        return new RestConfig();
    }

}
