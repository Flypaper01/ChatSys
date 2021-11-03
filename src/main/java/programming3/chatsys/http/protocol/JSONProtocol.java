package programming3.chatsys.http.protocol;

import org.json.JSONObject;
import programming3.chatsys.data.ChatMessage;
import programming3.chatsys.data.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class JSONProtocol {
    private BufferedReader reader;
    private BufferedWriter writer;

    public JSONProtocol(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public JSONProtocol(BufferedReader reader) {
        this.reader = reader;
    }

    public JSONProtocol(BufferedWriter writer) {
        this.writer = writer;
    }

    public User readUser() throws IOException {
        String json = "";
        String line;
        while ((line = this.reader.readLine()) != null) {
            json += line;
        }
        return new User(new JSONObject(json));
    }

    public void writeUser(User user) throws IOException {
        JSONObject json = user.toJSON();
        json.write(this.writer);
        this.writer.flush();
    }

    public List<ChatMessage> readMessages() {
        return null;
    }

    public void writeMessages(List<ChatMessage> messages) {

    }
}
