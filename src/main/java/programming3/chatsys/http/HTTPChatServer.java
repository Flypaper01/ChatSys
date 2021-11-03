package programming3.chatsys.http;

import com.sun.net.httpserver.HttpServer;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.Database;
import programming3.chatsys.data.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class HTTPChatServer {
    private final int port;
    private final Database database;

    public HTTPChatServer(int port, Database database) {
        this.port = port;
        this.database = database;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
        server.createContext("/user/", new UserHandler(this));
        server.createContext("/recent/", new RecentMessagesHandler(this));
        server.createContext("/unread/", new UnreadMessagesHandler(this));
        server.createContext("/message/", new MessageHandler(this));
        server.start();
        System.out.println("Server is running");
    }

    public boolean register(User user) {
        return this.database.register(user);
    }

    public List<ChatMessage> getRecentMessages(int n) {
        return this.database.getRecentMessages(n);
    }

    public List<ChatMessage> getUnreadMessages(String username) {
        return this.database.getUnreadMessages(username);
    }

    public boolean authenticate(User user) {
        return this.database.authenticate(user.getUserName(), user.getPassword());
    }
    public boolean authenticate(String username, String password) {
        return this.database.authenticate(username,password);
    }

    public ChatMessage addMessage(String username, String message) {
        return this.database.addMessage(username, message);
    }
}
