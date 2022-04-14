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
    private final LogLevel logLevel;

    public ProxyInitializer(String remoteHost, int remotePort, CounterRegistry counterRegistry, LogLevel logLevel) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.counterRegistry = counterRegistry;
        this.logLevel = logLevel;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
                new LoggingHandler(logLevel),
                new ProxyFrontendHandler(remoteHost, remotePort, counterRegistry));
    }
}