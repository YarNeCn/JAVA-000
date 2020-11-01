package io.github.kimmking.gateway.inbound.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by 14641 on 2020/10/31.
 */
public class CustomInBoundHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


    }
}
