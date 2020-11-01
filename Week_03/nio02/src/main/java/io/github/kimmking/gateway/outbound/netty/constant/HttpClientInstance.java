package io.github.kimmking.gateway.outbound.netty.constant;

import io.github.kimmking.gateway.outbound.netty.client.CustomHttpClient;
import io.github.kimmking.gateway.outbound.netty.client.HttpClient;
import io.github.kimmking.gateway.outbound.netty.client.NettyHttpClient;

/**
 * 为了不让重复创建客户端请求对象
 * Created by 14641 on 2020/11/1.
 */
public interface HttpClientInstance {

    HttpClient customHttpClient= new CustomHttpClient();

     HttpClient nettyHttpClient= new NettyHttpClient();

}
