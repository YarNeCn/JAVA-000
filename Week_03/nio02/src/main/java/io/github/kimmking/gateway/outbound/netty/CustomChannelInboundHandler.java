package io.github.kimmking.gateway.outbound.netty;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.outbound.netty.filter.DefualtHttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 14641 on 2020/10/31.
 */
public class CustomChannelInboundHandler extends ChannelInboundHandlerAdapter {
    //Netty服务端输出Channel处理
    public CustomChannelOutBoundHandler handler;

    //Http过滤器(暂时无序)
    private List<HttpRequestFilter> requestFilters = new ArrayList<>();

    private final String proxyServer;

    public CustomChannelInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        handler = new CustomChannelOutBoundHandler(this.proxyServer);
        initFilter();
    }

    /**
     * 提供添加过滤器方法
     *
     * @param requestFilter
     */
    public void addHttpRequestFilter(Object requestFilter) {
        if (requestFilter instanceof HttpRequestFilter) {
            requestFilters.add((HttpRequestFilter) requestFilter);
        }
        //这样里面也可以增加其他类型的Filter
    }

    //初始化过滤器
    public void initFilter() {
       if(requestFilters.size()==0){
           requestFilters.add(new DefualtHttpRequestFilter());
       }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        for(HttpRequestFilter httpRequestFilter:requestFilters){
            if(msg instanceof FullHttpRequest) {
                httpRequestFilter.filter((FullHttpRequest) msg, ctx);
            }
        }
        handler.excute(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
