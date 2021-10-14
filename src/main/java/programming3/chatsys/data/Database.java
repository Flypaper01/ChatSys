package programming3.chatsys.data;

import java.util.List;

/**
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public interface Database {
    /**
     * Add a user to the database if it is not yet inside.
     * @param user the user to add.
     * @return ture if the user could be added, false otherwise (i.e. username was already taken).
     * @throws DatabaseAccessException if the database cannot be accessed.
     */
    boolean register(User user);

    /**
     * Returns the number of users in the database.
     * @return The number of users in the database.
     * @throws DatabaseAccessException if the database cannot be accessed.
     */
    int getNumberUsers();

    /**
     * Returns a user based on a given user name.
     * @param userName The user's username.
     * @return The user object corresponding to the user name.
     * @throws DatabaseAccessException if the database cannot be accessed.
     * @throws IllegalArgumentException if the userName doesn't correspond to an existing user.
     */
    User getUser(String userName);

    /**
     * Check whether a pair of username and password are valid.
     * @param userName the name of the user.
     * @param password the password of the user.
     * @return true if the username and password are valid, false otherwise.
     * @throws DatabaseAccessException if the database cannot be accessed.
     */
    boolean authenticate(String userName, String password);

    /**
     * Adds a ChatMessage to the database.
     * @param userName user who sends the message.
     * @param message the message to add.
     * @return The ChatMessage object added.
     * @throws DatabaseAccessException if the database cannot be accessed.
     * @throws IllegalArgumentException if the userName doesn't correspond to an existing user.
     */
    ChatMessage addMessage(String userName, String message);

    /**
     * Returns the number of messages in the database.
     * @return The number of messages in the database.
     * @throws DatabaseAccessException if the database cannot be accessed.
     */
    int getNumberMessages();

    /**
     * Returns n recent chat messages from the database file.
     * @param n the number of chat messages to read.
     * @return the list of ChatMessages.
     * @throws DatabaseAccessException if the database cannot be accessed.
     */
    List<ChatMessage> getRecentMessages(int n);

    /**
     * Gets unread messages for a user and updates the user's last read id in the DB.
     * @param userName user.
     * @return Unread messages for the user.
     * @throws DatabaseAccessException if the database cannot be accessed.
     */
    List<ChatMessage> getUnreadMessages(String userName);

    /**
     * Closes the database and any connection if needed.
     * @throws DatabaseAccessException if the database cannot be accessed.
     */
    void close();
}
