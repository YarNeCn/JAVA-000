package io.github.kimmking.gateway.outbound.netty.router;

import io.github.kimmking.gateway.outbound.netty.constant.RouterHeaders;
import io.github.kimmking.gateway.router.HttpEndpointRouter;

import java.util.List;

/**
 * 这个Router的问题是只能针对同一个server
 * Created by 14641 on 2020/11/1.
 */
public class CustomDefualtRouter implements HttpEndpointRouter {

    private  RouterHeaders type;

    private Integer pos = 0;

    public CustomDefualtRouter(RouterHeaders type) {
        this.type = type;
    }

    /**
     * 默认选择负载
     *
     * @param endpoints
     * @return
     */
    @Override
    public String route(List<String> endpoints) {
        if (type != null && type == RouterHeaders.RANDOM) {
            return random(endpoints);
        }
        return posloadBanlance(endpoints);
    }

    /**
     * LoadBanlance
     *
     * @param endpoints
     * @return
     */
    private synchronized String posloadBanlance(List<String> endpoints) {
        String server;
        if (pos >= endpoints.size()) {
            pos = 0;
        }
        server = endpoints.get(pos);
        pos++;
        return server;
    }

    /**
     * RANDOM
     *
     * @param endpoints
     * @return
     */
    private synchronized String random(List<String> endpoints) {
        java.util.Random random = new java.util.Random();
        int pos = random.nextInt(endpoints.size());
        return endpoints.get(pos);
    }
}
