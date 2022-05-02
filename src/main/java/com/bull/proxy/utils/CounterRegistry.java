package com.bull.proxy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CounterRegistry {

    private final List<ByteCounter> byteCounters = new ArrayList<>();
    private final ByteCounterListener listener;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public CounterRegistry(ByteCounterListener counterListener) {
        this.listener = counterListener;
    }

    public synchronized ByteCounter register(String name) {
        var byteCounter = new ByteCounter(name, listener);
        byteCounters.add(byteCounter);
        return byteCounter;
    }

    public synchronized List<String> availableCounters() {
        return byteCounters.stream().map(ByteCounter::getName).collect(Collectors.toList());
    }
}
