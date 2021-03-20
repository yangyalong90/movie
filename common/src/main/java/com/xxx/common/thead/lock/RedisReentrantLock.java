package com.xxx.common.thead.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class RedisReentrantLock {

    private ThreadLocal<AtomicInteger> locker = new ThreadLocal<>();

    public void lock() {
        AtomicInteger integer;
        if ((integer = locker.get()) == null) {
            lock0();
            locker.set(new AtomicInteger(1));
            return;
        }
        integer.incrementAndGet();
    }

    public void unlock() {
        AtomicInteger integer;
        if ((integer = locker.get()) == null) {
            return;
        }
        int count = integer.decrementAndGet();
        if (count == 0) {
            unlock0();
        }
    }

    public void lock0() {

    }

    public void unlock0() {

    }

}
