package io.github.kimmking.gateway.outbound.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpResponse;

/**
 * Created by 14641 on 2020/10/31.
 * 这是Netty客户端的处理，给netty服务做响应
 */
public class NettyClientEchoHandler extends ChannelInboundHandlerAdapter {
    /**
     * netty服务端响应上下文
     */
    private final ChannelHandlerContext channelHandlerContext;

    public NettyClientEchoHandler(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //这里可以对msg响应的信息做调整，包括header,body等等

        if(msg instanceof DefaultHttpResponse){
            DefaultHttpResponse msg1 = (DefaultHttpResponse) msg;
            msg1.headers().add("client","netty");
        }
        //将结果返回出去
        channelHandlerContext.write(msg);
        channelHandlerContext.flush();
        //把客户端的通道关闭
        ctx.channel().close();

    }
}
