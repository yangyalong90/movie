package com.xxx.common.thead;

import com.xxx.common.thead.lock.RedisReentrantLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig {

    @Bean
    public RedisReentrantLock redisReentrantLock() {
        return new RedisReentrantLock();
    }

}
