import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {
    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(4000), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(Server::handleRequest);
        server.start();
        System.out.println("✅ Server is connected http://localhost:4000");
    }

    private static void handleRequest(HttpExchange req) throws IOException {
        String response = readFile("src/index.html", StandardCharsets.UTF_8);
        req.sendResponseHeaders(200, response.length());
        OutputStream os = req.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}