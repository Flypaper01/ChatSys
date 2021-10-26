package programming3.chatsys.data;

import java.sql.Timestamp;
import java.util.Objects;

public class ChatMessage {
    private int id;
    private String message;
    private String userName;
    private Timestamp timestamp;

    public ChatMessage(int id, String message, String userName, Timestamp timestamp) {
        this.id = id;
        this.message = message;
        this.userName = userName;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public ChatMessage(int lastid, String userName, String message) {
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
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", userName='" + userName + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


