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
    public String url;
    public String requestMethod;

    public HttpRequestModel(String url, String requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public JSONObject send(JSONObject json) throws IOException, InterruptedException {
        //创建 builder
        HttpClient.Builder builder = HttpClient.newBuilder();
        HttpClient client = builder.version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofMillis(50000))
                .followRedirects(HttpClient.Redirect.NEVER)
                .executor(Executors.newFixedThreadPool(5))
                .build();
        //创建 builder
        HttpRequest.Builder reBuilder = HttpRequest.newBuilder();
        HttpRequest request = reBuilder.header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create("http://localhost" + url + "?id=1"))
                .timeout(Duration.ofMillis(50000))
                .method(requestMethod, HttpRequest.BodyPublishers.ofString(json.toString()))
                //.method(requestMethod,HttpRequest.BodyPublishers.ofString("?id=1"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 200)
        {
            return JSONObject.fromObject(response.body());
        }
        else
        {
            return null;
        }
    }
}
