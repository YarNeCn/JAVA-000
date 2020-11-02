package io.github.kimmking.gateway.outbound.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Netty客户端
 * Created by 14641 on 2020/10/31.
 */
public class NettyHttpClient implements HttpClient {

    public void excute(String serverUrl, ChannelHandlerContext serverResponseChannelHandlerContext, FullHttpRequest msg) {
        URI uri = null;
        try {
            uri = new URI(serverUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String host = uri.getHost();
        int port = uri.getPort();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .option(EpollChannelOption.SO_REUSEPORT, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            //客户端超时处理
            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());

                    //客户端回调通知接收处理类，将server上下文传过去
                    ch.pipeline().addLast(new NettyClientEchoHandler(serverResponseChannelHandlerContext));
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).addListener(new ChannelFutureListener() {
                /**
                 * 添加netty客户端超时之后，服务端的处理
                 * @param channelFuture
                 * @throws Exception
                 */
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(!channelFuture.isSuccess()){
                        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
                        serverResponseChannelHandlerContext.write(response);
                        serverResponseChannelHandlerContext.flush();
                    }
                }
            }).sync();
            f.channel().write(msg);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
