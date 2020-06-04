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
    public JSONObject send(JSONObject json) throws IOException, InterruptedException {
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

                .method("get", HttpRequest.BodyPublishers.ofString(json.toString()))

                //创建完成
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject temp = JSONObject.fromObject(response.body());
        return temp;
    }
}
