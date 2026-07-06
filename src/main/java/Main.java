import org.apache.hc.core5.http.NameValuePair;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.classfile.constantpool.NameAndTypeEntry;
import java.nio.file.Files;
import java.util.List;

public class Main {
    static void main() {
        Server server = new Server();
        server.addHandlers(Request.Method.GET, "/forms.html", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream) {
                List<NameValuePair> params = request.queryParams;
                params.stream().forEach(System.out::println);
            }
        });

        server.addHandlers(Request.Method.GET, "/links.html", new Handler() {
            @Override
            public void handle(Request request, BufferedOutputStream responseStream) {
                System.out.println("Handler works");
                try {
                    final var length = Files.size(request.getFilePath());
                    final var mimeType = Files.probeContentType(request.getFilePath());
                    responseStream.write((
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: " + mimeType + "\r\n" +
                                    "Content-Length: " + length + "\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes());
                    Files.copy(request.getFilePath(), responseStream);
                    responseStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        server.addHandlers(Request.Method.GET, "/spring.png", new Handler() {
            @Override
            public void handle(Request request, BufferedOutputStream responseStream) {
                try {
                    final var length = Files.size(request.getFilePath());
                    final var mimeType = Files.probeContentType(request.getFilePath());
                    responseStream.write((
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: " + mimeType + "\r\n" +
                                    "Content-Length: " + length + "\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes());
                    Files.copy(request.getFilePath(), responseStream);
                    responseStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        server.start();
    }
}
