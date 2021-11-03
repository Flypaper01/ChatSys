package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.DatabaseAccessException;

import java.util.List;

public class RecentMessagesHandler extends AbstractHandler {
    private final HTTPChatServer server;

    public RecentMessagesHandler(HTTPChatServer server) {
        super(server);
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Got request from client");
        if (exchange.getRequestMethod().equals("GET")) {
            try {
                sendMessages(exchange, parseN(exchange));
            } catch(NumberFormatException e) {
                sendResponse(exchange, 400, "Number of chat messages should be provided in URL");
            }
        } else {
            sendResponse(exchange, 405, "Request method should be GET");
        }
    }

    private void sendMessages(HttpExchange exchange, int n) {
        if (n > 0) {
            List<ChatMessage> messages = this.server.getRecentMessages(n);
            try {
                sendMessages(exchange, messages);
            } catch(DatabaseAccessException e) {
                sendResponse(exchange, 500, "Database cannot be accessed.");
            }
        } else {
            sendResponse(exchange, 400, "Number of chat messages should be > 0");
        }
    }


    private int parseN(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String context = exchange.getHttpContext().getPath();
        return Integer.parseInt(path.substring(context.length()));
    }
}
