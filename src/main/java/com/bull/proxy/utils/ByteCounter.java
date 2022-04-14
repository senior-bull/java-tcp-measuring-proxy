package com.bull.proxy.utils;

import java.util.concurrent.atomic.AtomicLong;

public class ByteCounter {

    private final String name;
    private final ByteCounterListener counterListener;
    private final AtomicLong counter = new AtomicLong(0L);

    public ByteCounter(String name, ByteCounterListener counterListener) {
        this.name = name;
        this.counterListener = counterListener;
    }

    public void add(long delta) {
        this.counter.addAndGet(delta);
        counterListener.onCounterModified(this);
    }

    public long get() {
        return counter.get();
    }

    @Override
    public String toString() {
        return "ByteCounter{" +
                "name='" + name + '\'' +
                '}';
    }
}
