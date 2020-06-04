package MyHttp;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;

public class HttpRequestModel {
    public void send(JSONObject json) throws IOException, InterruptedException {
        //创建 builder
        HttpClient.Builder builder = HttpClient.newBuilder();

        //链式调用
        HttpClient client = builder
                //http 协议版本  1.1 或者 2
                .version(HttpClient.Version.HTTP_2) //.version(HttpClient.Version.HTTP_1_1)

                //连接超时时间，单位为毫秒
                .connectTimeout(Duration.ofMillis(5000)) //.connectTimeout(Duration.ofMinutes(1))

                //连接完成之后的转发策略
                .followRedirects(HttpClient.Redirect.NEVER) //.followRedirects(HttpClient.Redirect.ALWAYS)

                //指定线程池
                .executor(Executors.newFixedThreadPool(5))

                //认证，默认情况下 Authenticator.getDefault() 是 null 值，会报错
                //.authenticator(Authenticator.getDefault())

                //代理地址
                //.proxy(ProxySelector.of(new InetSocketAddress("http://www.baidu.com", 8080)))

                //缓存，默认情况下 CookieHandler.getDefault() 是 null 值，会报错
                //.cookieHandler(CookieHandler.getDefault())

                //创建完成
                .build();
        //创建 builder
        HttpRequest.Builder reBuilder = HttpRequest.newBuilder();

        //链式调用
        HttpRequest request = reBuilder

                //存入消息头
                //消息头是保存在一张 TreeMap 里的
                .header("Content-Type", "application/json")

                //http 协议版本
                .version(HttpClient.Version.HTTP_1_1)

                //url 地址
                .uri(URI.create("http://localhost:8080"))

                //超时时间
                .timeout(Duration.ofMillis(5009))

                //发起一个 post 消息，需要存入一个消息体
                //.POST(HttpRequest.BodyPublishers.ofString("hello"))

                //发起一个 get 消息，get 不需要消息体
                //.GET()

                .method("get", HttpRequest.BodyPublishers.ofString(json.toString())) //方法是 POST(...) 和 GET(...) 方法的底层，效果一样
                //.method("POST",HttpRequest.BodyPublishers.ofString("hello"))

                //创建完成
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject temp = JSONObject.fromObject(response.body());
        System.out.println(temp);
    }

}
