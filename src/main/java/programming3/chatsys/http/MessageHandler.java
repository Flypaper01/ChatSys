package programming3.chatsys.http;
import com.sun.net.httpserver.HttpExchange;
import programming3.chatsys.data.DatabaseAccessException;


public class MessageHandler extends AbstractHandler {
    private final HTTPChatServer server;

    public MessageHandler(HTTPChatServer server) {
        super(server);
        this.server = server;
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Got request from client");
        if (exchange.getRequestMethod().equals("POST")) {
            try {
                String username = handleAuthentication(exchange);
                String message = parseMessage(exchange);
                try {
                    server.addMessage(username, message);
                    sendResponse(exchange, 201, "operation successful");
                } catch (DatabaseAccessException e) {
                    sendResponse(exchange, 500, "Database cannot be accessed.");
                }
            } catch (NullPointerException e) {
                sendResponse(exchange, 401, "Authentication failed");
            }
        } else {
            sendResponse(exchange, 405, "Request method should be POST");
        }

    }



}
