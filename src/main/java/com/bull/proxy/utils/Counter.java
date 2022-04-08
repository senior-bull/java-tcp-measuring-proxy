package com.bull.proxy.utils;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {

    private final String name;
    private final AtomicLong counter = new AtomicLong(0L);

    public Counter(String name) {
        this.name = name;
    }

    public void add(long delta) {
        this.counter.addAndGet(delta);
    }

    public long get() {
        return counter.get();
    }
}
