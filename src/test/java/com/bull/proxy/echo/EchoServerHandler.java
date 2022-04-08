package com.bull.proxy.echo;

import com.bull.proxy.utils.Counter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private final Counter counter;

    public EchoServerHandler(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        final long bytes = ((ByteBuf) msg).readableBytes();
        ctx.write(msg).addListener(
                (ChannelFutureListener) channelFuture -> {
                    if (channelFuture.isSuccess()) {
                        counter.add(bytes);
                    }
                }
        );
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}