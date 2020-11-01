package io.github.kimmking.gateway.outbound.netty;

import io.github.kimmking.gateway.outbound.httpclient4.NamedThreadFactory;
import io.github.kimmking.gateway.outbound.netty.client.HttpClient;
import io.github.kimmking.gateway.outbound.netty.constant.HttpClientInstance;
import io.github.kimmking.gateway.outbound.netty.constant.HttpClientHeaders;
import io.github.kimmking.gateway.outbound.netty.constant.HttpHeaderConstant;
import io.github.kimmking.gateway.outbound.netty.constant.RouterHeaders;
import io.github.kimmking.gateway.outbound.netty.router.CustomDefualtRouter;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Netty服务器响应处理
 * Created by 14641 on 2020/10/31.
 */
public class CustomChannelOutBoundHandler {

    private final String proxyServers;
    private ExecutorService proxyService;

    private ConcurrentHashMap<String, HttpEndpointRouter> httpEndpointRouters = new ConcurrentHashMap<>();

    public CustomChannelOutBoundHandler(String proxyServers) {
        this.proxyServers = proxyServers;
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);
    }

    /**
     * 根据请求头确认Router以及客户端请求的方式
     *
     * @param ctx
     * @param msg
     */
    public void excute(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest msg11 = (FullHttpRequest) msg;
            proxyService.submit(() -> {
                HttpHeaders headers = msg11.headers();

                //第一步确认路由的方式
                HttpEndpointRouter httpEndpointRouter = null;
                if (httpEndpointRouters.containsKey(proxyServers)) {
                    httpEndpointRouter = httpEndpointRouters.get(proxyServers);
                } else {
                    String s = headers.get(HttpHeaderConstant.ROUTER_TYPE);
                    if (s == null || s.equals(RouterHeaders.LOADBANLANCE)) {
                        httpEndpointRouter = new CustomDefualtRouter(RouterHeaders.LOADBANLANCE);
                        httpEndpointRouters.put(proxyServers, httpEndpointRouter);
                    } else {
                        httpEndpointRouter = new CustomDefualtRouter(RouterHeaders.valueOf(s));
                        httpEndpointRouters.put(proxyServers, httpEndpointRouter);
                    }
                }

                //第二步确认请求的客户端类型
                HttpClient httpClient = null;
                String s1 = headers.get(HttpHeaderConstant.HTTP_CLIENT_TYPE);
                if (s1 == null || HttpClientHeaders.valueOf(s1).equals(HttpClientHeaders.HTTPCLIENT)) {
                    httpClient = HttpClientInstance.customHttpClient;
                } else {
                    httpClient = HttpClientInstance.nettyHttpClient;
                }


                //第三步确认请求的地址是单个还是多个，多个的话就路由出来一个
                String serverUrl = null;
                if (proxyServers.contains(",")) {
                    List<String> servers = Arrays.asList(proxyServers.split(","));
                    serverUrl = httpEndpointRouter.route(servers);
                } else {
                    serverUrl = proxyServers;
                }

                //执行Http客户端请求
                excuteHandler(ctx, msg11, serverUrl, httpClient);
            });
        } else {
            //非处理范围返回404
            unfindUri(ctx);
        }
    }

    /**
     * 执行客户端请求
     *
     * @param serverUrl
     * @param ctx
     * @param msg
     */
    public void excuteHandler(ChannelHandlerContext ctx, FullHttpRequest msg, String serverUrl, HttpClient httpClient) {
        //使用客户端进行处理request
        httpClient.excute(serverUrl, ctx, msg);
    }


    public void unfindUri(ChannelHandlerContext ctx) {
        HttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
        ctx.write(response);
    }
}
