package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class TestServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {
                System.out.println("Requested URI: " + exchange.getRequestURI());
                System.out.println("Client: " + exchange.getRemoteAddress());
                System.out.println("Request method: " + exchange.getRequestMethod());
                System.out.println("Request headers: " + exchange.getRequestHeaders().entrySet());
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                System.out.println("Request body: ");
                try {
                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                        System.out.println(line);
                    }
                } catch(IOException e) {
                    System.out.println(e);
                }
                String response = "Hello World";
                System.out.println("Sending response: " + response);
                try {
                    exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
                    writer.write(response);
                    writer.flush();
                    writer.close();
                } catch(IOException e) {
                    System.out.println(e);
                }
                System.out.println("Request sent");
                System.out.println();
            }
        });
        server.start();
        System.out.println("Server running");
    }
}
