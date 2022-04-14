package com.bull.proxy;

import com.bull.proxy.utils.CounterRegistry;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ProxyInitializer extends ChannelInitializer<SocketChannel> {

    private final String remoteHost;
    private final int remotePort;
    private final CounterRegistry counterRegistry;

    public ProxyInitializer(String remoteHost, int remotePort, CounterRegistry counterRegistry) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.counterRegistry = counterRegistry;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
                new LoggingHandler(LogLevel.INFO),
                new ProxyFrontendHandler(remoteHost, remotePort, counterRegistry));
    }
}