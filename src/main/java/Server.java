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

    ConcurrentMap<Request.Method, ConcurrentMap<String, Handler>> methodToPathAndHandler = new ConcurrentHashMap<>();

    public void start() {
         ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

         try (final var serverSocket = new ServerSocket(9999)) {
             while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(() -> {
                    System.out.println("new socket was opened");
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
            System.out.println("Request was parsed");
            if (parsedRequest.isEmpty()) {
                System.out.println("Request is empty, closing socket");
                return;
            }
            handleSocket(parsedRequest.get(), out);
            System.out.println("Socket was handled");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandlers(Request.Method requestMethod, String requestedPath, Handler handler) {
        System.out.println("Adding handler for path: \"" + requestedPath + '"');
        methodToPathAndHandler.putIfAbsent(requestMethod, new ConcurrentHashMap<>());
        methodToPathAndHandler.get(requestMethod).put(requestedPath, handler);
    }

    void handleSocket(Request request, BufferedOutputStream responseStream) {
        var methodMap = methodToPathAndHandler.getOrDefault(request.getMethod(), null);

        if (methodMap == null) {
            System.out.println("No handler for this METHOD was found");
            return;
        }

        System.out.println("Handing the socket");
        Handler handler = methodMap.getOrDefault(request.getPath(), null);
        if (handler != null) {
            System.out.println("Handler was found, handling");
            handler.handle(request, responseStream);
        } else {
            System.out.println("No handler for this PATH was found");
        }
    }

    Optional<Request> parseRequest(BufferedReader in) throws IOException {
        if (in == null) return Optional.empty();

        System.out.println("Parsing request");

        // parse request line
        String requestLine = in.readLine(); // don't handle it since processSocket may have special logic for Reader closure
        var reqLineParts = requestLine.split(" ");
        if (reqLineParts.length != 3) return Optional.empty();

        Request.Method method = Request.Method.valueOf(reqLineParts[0]);

        System.out.println("Parsing headers");

        // parse headers
        String line;
        List<String> headers = new ArrayList<>();
        while ((line = in.readLine()) != null) {
            if (line.equals("\r\n") || line.isEmpty()) {
                // we've reached body and headers have ended
                break;
            }
            headers.add(line);
        }

        System.out.println("Parsing body");

        // parse body
        StringBuilder bodyBuilder = new StringBuilder();
        if (method != Request.Method.GET) {
            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) break;
                bodyBuilder.append(line);
            }
        }

        System.out.println("All was parsed, returning parsed request");

        return Optional.of(new Request(
                method, reqLineParts[1], reqLineParts[2],
                headers,
                (bodyBuilder.isEmpty() ? "" : bodyBuilder.toString())));
    }
}
