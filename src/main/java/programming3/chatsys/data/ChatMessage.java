package programming3.chatsys.data;

import java.sql.Timestamp;
import java.util.Objects;


public class ChatMessage {
    private int id;
    private String message;
    private String userName;
    private Timestamp timestamp;


    public ChatMessage(int id, String userName, Timestamp timestamp, String message) {
        this.init(id, userName, timestamp, message);
    }

    public ChatMessage(int id, String userName, long timestamp, String message) {
        this.init(id, userName, new Timestamp(timestamp), message);
    }

    public ChatMessage(int id, String userName, String message) {
        this.init(id, userName, new Timestamp(System.currentTimeMillis()), message);
    }

    public ChatMessage() {

    }

    private void init(int id, String userName, Timestamp timestamp, String message) {
        if (message.indexOf('\n') >= 0) {
            throw new IllegalArgumentException("message contains a line feed");
        }
        this.id = id;
        this.userName = userName;
        this.timestamp = timestamp;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return id == that.id &&
                Objects.equals(message, that.message) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, userName, timestamp);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", userName='" + userName + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
