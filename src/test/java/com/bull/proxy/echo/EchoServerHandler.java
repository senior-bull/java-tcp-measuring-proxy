package com.bull.proxy.echo;

import com.bull.proxy.utils.ByteCounter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private final ByteCounter byteCounter;

    public EchoServerHandler(ByteCounter byteCounter) {
        this.byteCounter = byteCounter;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        final long bytes = ((ByteBuf) msg).readableBytes();
        ctx.write(msg).addListener(
                (ChannelFutureListener) channelFuture -> {
                    if (channelFuture.isSuccess()) {
                        byteCounter.add(bytes);
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