package com.bull.proxy.utils;

import io.netty.channel.Channel;

public class ConnectionNameGenerator {

    public String getConnectionName(Channel inboundChannel) {
        return "Connection"; // TODO
    }
}
