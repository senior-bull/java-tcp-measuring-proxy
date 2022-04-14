package com.bull.proxy.echo;

import com.bull.proxy.utils.ByteCounter;
import com.bull.proxy.utils.ByteCounterListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public final class EchoServer implements AutoCloseable {

    private final int port;
    private final ByteCounter byteCounter;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public EchoServer(int port) {
        this.port = port;
        this.byteCounter = new ByteCounter("EchoServer-" + port, ByteCounterListener.EMPTY);
        start();
    }

    private void start() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new EchoServerHandler(byteCounter));
                    }
                });

        try {
            b.bind(port).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        return port;
    }

    public long getBytesProcessed() {
        return byteCounter.get();
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        EchoServer echoServer = new EchoServer(8007);
        Thread.sleep(50000000L);
    }
}