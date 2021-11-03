package programming3.chatsys.data;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException(Throwable cause) {
        super(cause);
    }

    public DatabaseAccessException() {
    }

    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
