package com.bull.proxy.utils;

import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicLong;

public class ConnectionNameGenerator {

    public static final AtomicLong idGenerator = new AtomicLong(0L);

    public static String getConnectionName(String mod, Channel inboundChannel) {
        return "Connection-" + mod + "-" + idGenerator.incrementAndGet() + "-" + extractAddress(inboundChannel);
    }

    private static String extractAddress(Channel channel) {
        SocketAddress socketAddress = channel.remoteAddress();
        if (socketAddress != null) {
            return socketAddress.toString();
        }
        socketAddress = channel.localAddress();
        if (socketAddress != null) {
            return socketAddress.toString();
        }
        return "-";
    }
}
