package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import programming3.chatsys.data.DatabaseAccessException;
import programming3.chatsys.data.User;

import java.io.*;

public class UserHandler extends AbstractHandler {
    private final HTTPChatServer server;

    public UserHandler(HTTPChatServer server) {
        super(server);
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Got request from client");
        if (exchange.getRequestMethod().equals("POST")) {
            User user = parseUser(exchange);
            if (user != null) {
                handleUser(exchange, user);
            }
        } else {
            sendResponse(exchange, 405, "Request method should be POST");
        }
    }


    private void handleUser(HttpExchange exchange, User user) {
        System.out.println("User information received from client: " + user);
        try {
            if (this.server.register(user)) {
                sendResponse(exchange, 201, "User " + user.getUserName() + " was successfully registered");
            } else {
                sendResponse(exchange, 400, "User " + user.getUserName() + " cannot be registered (e.g. username already taken)");
            }
        } catch(DatabaseAccessException e) {
            sendResponse(exchange, 500, "Database cannot be accessed.");
        }
    }
}
