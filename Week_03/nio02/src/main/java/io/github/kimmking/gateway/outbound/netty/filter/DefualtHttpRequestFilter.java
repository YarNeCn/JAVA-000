package io.github.kimmking.gateway.outbound.netty.filter;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.outbound.netty.constant.HttpClientHeaders;
import io.github.kimmking.gateway.outbound.netty.constant.HttpHeaderConstant;
import io.github.kimmking.gateway.outbound.netty.constant.RouterHeaders;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

/**
 * Created by 14641 on 2020/11/1.
 */
public class DefualtHttpRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        HttpHeaders headers = fullRequest.headers();
        if(!headers.contains(HttpHeaderConstant.ROUTER_TYPE)){
            fullRequest.headers().add(HttpHeaderConstant.ROUTER_TYPE, RouterHeaders.LOADBANLANCE);
        }
        //作业
        fullRequest.headers().add("nio","taoyannan");
        //根据这里传输的HTTPCLIENT或者NETTY在浏览器的响应头中可以看到
        fullRequest.headers().add(HttpHeaderConstant.HTTP_CLIENT_TYPE, HttpClientHeaders.NETTY);
    }
}
