package com.bull.proxy;

import com.bull.proxy.utils.ByteCounter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProxyBackendHandler extends ChannelInboundHandlerAdapter {

    private final Channel inboundChannel;
    private final ByteCounter inBytesByteCounter;

    public ProxyBackendHandler(Channel inboundChannel, ByteCounter byteCounter) {
        this.inboundChannel = inboundChannel;
        this.inBytesByteCounter = byteCounter;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.read();
    }

    public ByteCounter getCounters() {
        return inBytesByteCounter;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        final long bytes = buf.readableBytes();
        inboundChannel.writeAndFlush(buf).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                ctx.channel().read();
                inBytesByteCounter.add(bytes);
            } else {
                future.channel().close();
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ProxyFrontendHandler.closeOnFlush(inboundChannel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ProxyFrontendHandler.closeOnFlush(ctx.channel());
    }
}