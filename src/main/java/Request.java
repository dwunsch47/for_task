import java.util.List;

public class Request {
    public enum Method {
        GET,
        POST
    };

    Method method;
    String path;
    String httpVersion;
    List<String> headers;
    String body = null;

    public Request(Method method, String path, String httpVersion, List<String> headers, String body) {
        this(method, path, httpVersion, headers);
        this.body = body;
    }

    public Request(Method method, String path, String httpVersion, List<String> headers) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
    }

     public Method getMethod() {
        return method;
     }

     public String getPath() {
        return path;
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
}
