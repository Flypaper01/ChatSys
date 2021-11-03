package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.DatabaseAccessException;
import programming3.chatsys.data.User;

import java.io.*;
import java.util.List;

public abstract class AbstractHandler implements HttpHandler {
    protected final HTTPChatServer server;

    protected AbstractHandler(HTTPChatServer server) {
        this.server = server;
    }


    public abstract void handle(HttpExchange exchange);

    protected void sendResponse(HttpExchange exchange, int responseCode, String response) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()))) {
            exchange.sendResponseHeaders(responseCode, 0);
            writer.write(response);
        } catch (IOException e) {
            System.out.println("Couldn't send response to client.");
        }
        exchange.close();
    }

    protected User parseUser(HttpExchange exchange) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            String line = reader.readLine();
            System.out.println(line);
            String[] split = line.split("\t", 3);
            if (split.length < 3) {
                String response = "User information in request body was not formatted properly\n";
                response += "User should be formatted as <username>\\t<fullname>\\t<password>";
                sendResponse(exchange, 400, response);
            } else {
                return new User(split[0], split[1], split[2]);
            }
        } catch (IOException e) {
            sendResponse(exchange, 400, "Cannot read from request body.");
        } catch (NullPointerException e) {
            sendResponse(exchange, 400, "No response body provided.");
        }
        return null;
    }

    protected String parseMessage(HttpExchange exchange) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            return reader.readLine();
        } catch (IOException | NullPointerException e) {
            sendResponse(exchange, 400, "parsing error or message missing in request body");
        } catch (DatabaseAccessException e) {
            sendResponse(exchange, 500, "Database cannot be accessed.");
        }
        return null;
    }

    protected void sendMessages(HttpExchange exchange, List<ChatMessage> messages) {
        StringBuilder response = new StringBuilder();
        for (ChatMessage message : messages) {
            response.append(message.getId()).append("\t").append(message.getMessage()).append("\t");
            response.append(message.getUserName()).append("\t").append(message.getTimestamp().getTime()).append("\r\n");
        }
        sendResponse(exchange, 200, response.toString());
    }

    protected String handleAuthentication(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        if (query != null) {
            String[] parametres = query.split("&");
            if (parametres.length < 2) {
                sendResponse(exchange, 400, "parsing error");
            } else {
                String username= parametres[0].split("=")[1];
                String password = parametres[1].split("=")[1];
                try {
                    if (!server.authenticate(username, password)) {
                        sendResponse(exchange, 401, "Authentication failed");
                    } else {
                        return username;
                    }
                } catch (DatabaseAccessException e) {
                    sendResponse(exchange, 500, "Database cannot be accessed.");
                }
            }
        } else {
            sendResponse(exchange, 400, "parsing error");
        }
        return null;
    }
}
