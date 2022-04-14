package com.bull.proxy.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SysOutLoggingListener implements ByteCounterListener {

    private final Map<ByteCounter, Long> counters = new ConcurrentHashMap<>();

    @Override
    public void onCounterRegistered(ByteCounter byteCounter) {
        System.out.println("Registered byte counter " + byteCounter.toString());
    }

    @Override
    public void onCounterModified(ByteCounter byteCounter) {
        long lastTimePrinted = counters.getOrDefault(byteCounter, (long) 0L);
        long now = System.currentTimeMillis();
        if (now - lastTimePrinted > 1000) {
            System.out.println("Counter " + byteCounter.toString() + ", new value: " + byteCounter.get());
            counters.put(byteCounter, now);
        }
    }

    @Override
    public void onCounterClosed(ByteCounter byteCounter) {

    }
}
