package io.github.kimmking.gateway.outbound.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created by 14641 on 2020/11/1.
 */
public interface HttpClient {

     void excute(String serverUrl, ChannelHandlerContext serverResponseChannelHandlerContext, FullHttpRequest msg);
}
