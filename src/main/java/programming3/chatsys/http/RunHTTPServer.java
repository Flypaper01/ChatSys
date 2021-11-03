package programming3.chatsys.http;

import programming3.chatsys.data.Database;
import programming3.chatsys.data.InMemoryDatabase;
import programming3.chatsys.data.User;

import java.io.IOException;

public class RunHTTPServer {
    public static void main(String[] args) {
        Database db = new InMemoryDatabase();
        HTTPChatServer server = new HTTPChatServer(80, db);
        try {
            server.start();
        } catch (IOException e) {
            System.out.println("Server could not be started.");
            e.printStackTrace();
        }
    }
}
