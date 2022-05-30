package com.example.excitingform;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.*;
import com.sun.net.httpserver.HttpServer;

public class Server {

    public static void main(String[] args) throws IOException {
        create(8000).start();
    }

    public static HttpServer create(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/signup", (httpExchange) -> {
            String html = Files.readString(Paths.get("src/main/resources/exciting.html"));
            httpExchange.sendResponseHeaders(200, html.length());
            try (OutputStreamWriter os = new OutputStreamWriter(httpExchange.getResponseBody())) {
                os.write(html);
            }
        });
        server.setExecutor(null);
        return server;
    }
}