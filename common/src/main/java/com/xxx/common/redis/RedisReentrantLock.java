package com.xxx.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RedisReentrantLock {

    private static final ThreadLocal<Map<String, Integer>> LOCKED_KEY_COUNT = new ThreadLocal<>();

    private RedisTemplate redisTemplate;

    public boolean lock(String key){
        Map<String, Integer> lockers = lockers();
        Integer integer = lockers.get(key);
        if (integer == null) {
            if(!lock0(key)) {
                return false;
            }
            integer = 0;
        }
        lockers.put(key, ++integer);
        return true;
    }

    public void unlock(String key){
        Map<String, Integer> lockers = lockers();
        Integer integer = lockers.get(key);
        if (integer == null) {
            return;
        }
        integer--;
        if (integer > 0) {
            lockers.put(key, integer);
        }else {
            lockers.remove(key);
            unlock0(key);
        }
    }

    private Map<String, Integer> lockers(){
        Map<String, Integer> map = LOCKED_KEY_COUNT.get();
        if (map == null) {
            map = new HashMap<>();
            LOCKED_KEY_COUNT.set(map);
        }
        return map;
    }

    private boolean lock0(String key){
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>("set lock:biz:pay true ex 5 nx",
                Boolean.class);
//        redisTemplate.execute(redisScript)
        return true;
    }

    private void unlock0(String key){}
}
