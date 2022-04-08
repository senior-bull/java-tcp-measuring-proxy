package com.bull.proxy.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public final class EchoClient {

    private final String host = "127.0.0.1";
    private final int port;
    private final int messageSize;

    public EchoClient(int port, int messageSize) {
        this.port = port;
        this.messageSize = messageSize;
    }

    public void sendMessage() {
        EchoClientHandler echoClientHandler = new EchoClientHandler(messageSize);

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.DEBUG));
                            p.addLast(echoClientHandler);
                        }
                    });

            ChannelFuture f = b.connect("127.0.0.1", port).sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException ie) {
            throw new RuntimeException("Interrupted", ie);
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient(8007, 256);
        echoClient.sendMessage();
    }
}