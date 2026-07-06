import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Request {
    public enum Method {
        GET,
        POST
    };


    Method method;

    String path;

    List<NameValuePair> queryParams;

    String httpVersion;

    List<String> headers;
    String body = null;

    public Request(Method method, String path, String httpVersion, List<String> headers, String body) {
        this(method, path, httpVersion, headers);
        this.body = body;
    }

    public Request(Method method, String path, String httpVersion, List<String> headers) {
        this.method = method;
        parsePathAndQueryParams(path);
        this.httpVersion = httpVersion;
        this.headers = headers;
    }

     public Method getMethod() {
        return method;
     }

     public String getPath() {
        return path;
     }

     public Path getFilePath() { return Path.of(".", "public", path); }

     public List<NameValuePair> getQueryParams() { return queryParams; };

    public NameValuePair getQueryParam(String queryName) {
        return queryParams.stream().filter(q -> q.getName().equals(queryName)).findFirst().orElse(new BasicNameValuePair("", ""));
    }

     public String getHttpVersion() {
        return httpVersion;
     }

     public List<String> getHeaders() {
        return headers;
     }

     public String getBody() {
        return (body == null ? "" : body);
     }

     void parsePathAndQueryParams(String path) {
        int questionMarkIndex = path.indexOf('?');
        this.path = path.substring(0, (questionMarkIndex == -1 ? path.length() : questionMarkIndex));
        this.queryParams = URLEncodedUtils.parse(path.substring(questionMarkIndex + 1), StandardCharsets.UTF_8);
     }
}
