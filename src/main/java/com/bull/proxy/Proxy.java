package com.bull.proxy;

import com.bull.proxy.utils.CounterRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class Proxy implements AutoCloseable {

    private final int localPort;
    private final String remoteHost;
    private final int remotePort;
    private final CounterRegistry counterRegistry;
    private final LogLevel logLevel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public Proxy(int localPort, String remoteHost, int remotePort, CounterRegistry counterRegistry, LogLevel logLevel) {
        this.localPort = localPort;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.counterRegistry = counterRegistry;
        this.logLevel = logLevel;
        start();
    }

    private void start() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        try {
            ServerBootstrap channel = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class);
            channel = channel.handler(new LoggingHandler(logLevel))
                    .childHandler(new ProxyInitializer(remoteHost, remotePort, counterRegistry, logLevel));

            channel.childOption(ChannelOption.AUTO_READ, false).bind(localPort).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            bossGroup.shutdownGracefully();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}