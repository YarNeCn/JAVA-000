# netty-gateway

```
1. 作业中个人主要写的代码在io.github.kimmking.gateway.outbound.netty这个包下
2. 将老师的io.github.kimmking.gateway.inbound.HttpInboundInitializer中的channelInBound替换为了自己的处理
3. 可以在filter中根据header修改请求的客户端，可以在响应中看到最终netty服务器请求使用的客户端
4. Router有个问题是，loadbanlance情况下我根据请求的次数来确定走哪个router，但是浏览器一次会发三个请求
   

```