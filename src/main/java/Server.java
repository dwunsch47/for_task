import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    final int NUMBER_OF_THREADS = 64;
    final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");

    ConcurrentMap<String, Handler> getHandlers = new ConcurrentHashMap<>();
    ConcurrentMap<String, Handler> postHandlers = new ConcurrentHashMap<>();

    public void start() {
         ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

         try (final var serverSocket = new ServerSocket(9999)) {
             while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(() -> {
                    processSocket(socket);
                });
             }
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             executor.shutdown();
         }
    }

    void processSocket(Socket socket) {
        try (
                final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final var out = new BufferedOutputStream(socket.getOutputStream());
        ) {
            Optional<Request> parsedRequest = parseRequest(in);
            if (parsedRequest.isEmpty()) {
                return;
            }
            handleSocket(parsedRequest.get(), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandlers(String requestMethod, String requestedPath, Handler handler) {
        var methodMap = switch (requestMethod) {
            case "GET" -> getHandlers;
            case "POST" -> postHandlers;
            default -> null;
        };

        if (methodMap != null) methodMap.put(requestedPath, handler);
    }

    void handleSocket(Request request, BufferedOutputStream responseStream) {
        var methodMap = switch (request.getMethod()) {
            case Request.Method.GET -> getHandlers;
            case Request.Method.POST -> postHandlers;
            default -> null;
        };

        if (methodMap == null) {
            return;
        }

        Handler handler = methodMap.getOrDefault(request.getPath(), null);
        if (handler != null) handler.handle(request, responseStream);
    }

    Optional<Request> parseRequest(BufferedReader in) throws IOException {
        if (in == null) return Optional.empty();

        // parse request line
        String requestLine = in.readLine(); // don't handle it since processSocket may have special logic for Reader closure
        var reqLineParts = requestLine.split(" ");

        if (reqLineParts.length != 3) return Optional.empty();

        // parse headers
        String line;
        List<String> headers = new ArrayList<>();
        while ((line = in.readLine()) != null) {
            if (line.equals("\r\n")) {
                // we've reached body and headers have ended
                break;
            }
            headers.add(line);
        }

        // parse body
        StringBuilder bodyBuilder = new StringBuilder();
        while ((line = in.readLine()) != null) {
            bodyBuilder.append(line);
        }

        return Optional.of(new Request(
                Request.Method.valueOf(reqLineParts[0]), reqLineParts[1], reqLineParts[2],
                headers,
                (bodyBuilder.isEmpty() ? "" : bodyBuilder.toString())));
    }
}
