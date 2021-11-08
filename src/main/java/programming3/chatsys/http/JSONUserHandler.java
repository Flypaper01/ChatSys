package programming3.chatsys.http;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONException;
import programming3.chatsys.data.User;
import programming3.chatsys.http.protocol.JSONProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONUserHandler extends UserHandler {
    public JSONUserHandler(HTTPChatServer server) {
        super(server);
    }

    @Override
    protected User parseUser(HttpExchange exchange) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
            return new JSONProtocol(reader).readUser();
        } catch (IOException e) {
            sendResponse(exchange, 400, "Cannot read from request body.");
        } catch(JSONException e) {
            sendResponse(exchange, 400, "Error while parsing request body as a JSON object: " + e.getMessage());
        }
        return null;
    }
}
