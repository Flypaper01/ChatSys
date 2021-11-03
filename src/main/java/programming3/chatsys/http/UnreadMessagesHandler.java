package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.DatabaseAccessException;

import java.util.List;


public class UnreadMessagesHandler extends AbstractHandler {
    private final HTTPChatServer server;

    public UnreadMessagesHandler(HTTPChatServer server) {
        super(server);
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Got request from client");
        if (exchange.getRequestMethod().equals("GET")) {
            String userName = handleAuthentication(exchange);
            if (userName != null) {
                sendMessages(exchange, userName);
            }

        } else {
            sendResponse(exchange, 405, "Request method should be GET");
        }

    }

    private void sendMessages(HttpExchange exchange, String userName) {
        try {
            List<ChatMessage> messages = this.server.getUnreadMessages(userName);
            sendMessages(exchange, messages);
        } catch (DatabaseAccessException e) {
            sendResponse(exchange, 500, "Database cannot be accessed.");
        }
    }
}

