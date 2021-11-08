package programming3.chatsys.data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabase implements Database{
    private final Connection connection ;
    private ChatMessage chatMessage;
    private List<ChatMessage> messages;
    private User user ;


    public SQLiteDatabase(Connection connection) throws SQLException {
        this.connection = connection;
        this.createUserTable();
        this.createMessagesTable();
    }



    private void  createUserTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS user (\n" +
                "id integer PRIMARY KEY,\n" +
                "username text UNIQUE NOT NULL,\n" +
                "fullname text NOT NULL,\n" +
                "password text NOT NULL,\n" +
                "last_read_id integer DEFAULT 0\n" +
                ");\n";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
    }

    private void createMessagesTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS chatmessage (\n" +
                "id integer PRIMARY KEY,\n" +
                "user integer NOT NULL,\n" +
                "time integer NOT NULL,\n" +
                "message text NOT NULL\n" +
                ");\n";
        Statement statement = this.connection.createStatement();
        statement.execute(query);
    }

    @Override
    public boolean register(User user) {
        String query = "INSERT INTO user(username, fullname, password) VALUES(?, ?, ?)";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getPassword());
            return statement.executeUpdate() == 1;
        } catch(SQLException e) {
            if (e.getErrorCode() == 19) {
                return false;
            } else {
                throw new DatabaseAccessException(e);
            }
        }
    }

    @Override
    public int getNumberUsers() {
        String query = "SELECT COUNT(*) FROM user";
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(query);
            return statement.getResultSet().getInt(1);
        } catch(SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public User getUser(String userName) {
        String query = "SELECT * FROM user WHERE username = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()) {
                return new User(
                        result.getString("username"),
                        result.getString("fullname"),
                        result.getString("password")
                );
            } else {
                throw new IllegalArgumentException(userName + " is not a registered user");
            }
        } catch(SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public boolean authenticate(String userName, String password) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.execute();
            ResultSet result = statement.getResultSet();
            return result.getInt(1) == 1;
        } catch(SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public ChatMessage addMessage(String userName, String message) {
        String query = "INSERT INTO chatmessage(user, time, message) SELECT id, ?, ? FROM user WHERE username = \n" +
                "?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1,userName);
            statement.setString(2,message);
            statement.execute();
            return chatMessage;
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public int getNumberMessages() {
       String query = "SELECT COUNT(*) FROM message";
       try {
           Statement statement = this.connection.createStatement();
           statement.execute(query);
           return statement.getResultSet().getInt(1);
       }catch (SQLException e){
           throw new DatabaseAccessException();
       }
    }

    @Override
    public List<ChatMessage> getRecentMessages(int n) {
        String query = "SELECT * FROM message WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1,n);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()){
                return messages.subList(this.messages.size()-n,this.messages.size());
            }
            else {
                return new LinkedList<>(messages);
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String userName) {
        String query = "SELECT * FROM message WHERE user = ? ";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1,userName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            if (result.next()){
                int firstUnread = 0;
                final int lastReadId = user.getLastReadId();
                for (ChatMessage m : this.messages) {
                    firstUnread = m.getId();
                    if (firstUnread > lastReadId) {
                        break;
                    }
                }
                return this.messages.subList(firstUnread - 1, this.messages.size());
            }else {
                return new LinkedList<>();
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException(e);
        }

    }

    @Override
    public void close() {
        this.close();
    }


}
