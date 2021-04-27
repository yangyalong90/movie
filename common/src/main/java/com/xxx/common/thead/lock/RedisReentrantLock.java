package com.xxx.common.thead.lock;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author 86130
 */
public class RedisReentrantLock {

    /**
     * 存储线程下的所有 redis lock
     */
    private static final ThreadLocal<Map<String, LockedIdAndCount>> LOCKED_KEY_COUNT = new ThreadLocal<>();
    private static final int LOCK_TIMEOUT = 5;

    private RedisTemplate<String, String> redisTemplate;

    public RedisReentrantLock(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean lock(String key) {
        Map<String, LockedIdAndCount> lockers = lockers();
        LockedIdAndCount lockedIdAndCount = lockers.get(key);
        // 如果当前线程无锁，执行加锁过程
        if (lockedIdAndCount == null) {
            // 当前线程持有锁的 id，解锁时比对
            String lockedId = UUID.randomUUID().toString();
            // 执行加锁
            if (!lock0(key, lockedId)) {
                return false;
            }
            lockedIdAndCount = new LockedIdAndCount(lockedId);
        }
        // 计数 +1
        lockedIdAndCount.incr();
        lockers.put(key, lockedIdAndCount);
        return true;
    }

    public void unlock(String key) {
        Map<String, LockedIdAndCount> lockers = lockers();
        LockedIdAndCount lockedIdAndCount = lockers.get(key);
        if (lockedIdAndCount == null) {
            return;
        }
        // 计数 -1
        if (lockedIdAndCount.decr() <= 0) {
            // 比对 redis lock id 和 当前线程 lock id，如果不相等，放弃解锁
            if (!ObjectUtils.equals(redisTemplate.opsForValue().get(key), lockedIdAndCount.id)) {
                return;
            }
            // 解锁
            lockers.remove(key);
            unlock0(key);
            if (lockers.isEmpty()) {
                LOCKED_KEY_COUNT.remove();
            }
        }
    }

    private Map<String, LockedIdAndCount> lockers() {
        Map<String, LockedIdAndCount> map = LOCKED_KEY_COUNT.get();
        if (map == null) {
            map = new HashMap<>(16);
            LOCKED_KEY_COUNT.set(map);
        }
        return map;
    }

    private boolean lock0(String key, String lockedId) {
        Boolean lock = redisTemplate.opsForValue().setIfPresent(key, lockedId, LOCK_TIMEOUT, TimeUnit.SECONDS);
        if (lock == null || !lock) {
            return false;
        }
        return true;
    }

    private void unlock0(String key) {
        redisTemplate.delete(key);
    }

    class LockedIdAndCount {
        int count = 0;
        String id;

        public LockedIdAndCount(String id) {
            this.id = id;
        }

        void incr() {
            count++;
        }

        int decr() {
            return --count;
        }
    }

}
