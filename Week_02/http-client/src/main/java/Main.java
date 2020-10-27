import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 第三题作业，不是特别清楚老师的目的
 * Created by 14641 on 2020/10/27.
 */
public class Main {

    static HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

    public void request(String uri) throws IOException {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CloseableHttpClient build = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse execute = build.execute(httpGet);
        try(InputStream content = execute.getEntity().getContent()){
            int available =content.available();
            byte[] bytes=new byte[available];
            content.read(bytes);
            System.out.println(new String(bytes));
            execute.close();
        }

    }
    public void synchronizeRequest(String uri,int num) throws IOException, InterruptedException {
        for (int i=0;i<num;i++){
            CloseableHttpClient build = httpClientBuilder.build();
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse execute = build.execute(httpGet);
            try(InputStream content = execute.getEntity().getContent()){
                int available =content.available();
                byte[] bytes=new byte[available];
                content.read(bytes);
                System.out.println(new String(bytes));
                execute.close();
            }
        }
    }

    public void asynchronousRequest(String uri,int num) throws InterruptedException {
        for (int i=0;i<num;i++){
            Runnable runnable=()->{
                try {
                    CloseableHttpClient build = httpClientBuilder.build();
                    HttpGet httpGet = new HttpGet(uri);
                    CloseableHttpResponse execute = build.execute(httpGet);
                    try(InputStream content = execute.getEntity().getContent()){
                        int available =content.available();
                        byte[] bytes=new byte[available];
                        content.read(bytes);
                        System.out.println(new String(bytes));
                        execute.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            runnable.run();

        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Main main = new Main();
        long start = System.currentTimeMillis();
        main.asynchronousRequest("http://localhost:8808/test",10000);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
