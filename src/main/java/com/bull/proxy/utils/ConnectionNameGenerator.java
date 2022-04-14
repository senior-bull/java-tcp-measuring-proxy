package com.bull.proxy.utils;

import io.netty.channel.Channel;

import java.util.concurrent.atomic.AtomicLong;

public class ConnectionNameGenerator {

    public static final AtomicLong idGenerator = new AtomicLong(0L);

    public static String getConnectionName(String mod, Channel inboundChannel) {
        return "Connection-" + mod + "-" + idGenerator.incrementAndGet() + "-" + inboundChannel.remoteAddress().toString();
    }
}
