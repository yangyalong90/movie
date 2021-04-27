package com.xxx.common.thead;

import com.xxx.common.thead.lock.RedisReentrantLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ThreadConfig {

    @Bean
    public RedisReentrantLock redisReentrantLock(RedisTemplate redisTemplate) {
        return new RedisReentrantLock(redisTemplate);
    }

}
