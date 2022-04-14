package com.bull.proxy.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final int messageSize;
    private final int messageCount;

    public EchoClientHandler(int messageByteSize, int messageCount) {
        this.messageSize = messageByteSize;
        this.messageCount = messageCount;
    }

    private ByteBuf generateMessage() {
        ByteBuf message = Unpooled.buffer(messageSize);
        for (int i = 0; i < message.capacity(); i ++) {
            message.writeByte((byte) i);
        }
        return message;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < messageCount; i++) {
            ctx.writeAndFlush(generateMessage());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.close();
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