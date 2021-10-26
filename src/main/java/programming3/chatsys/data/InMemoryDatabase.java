package programming3.chatsys.data;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class InMemoryDatabase implements Database {
    private List<User> users;
    private int lastid = 0;
    private List<ChatMessage> messages;

    public InMemoryDatabase() {
        users = new LinkedList<>();
        messages = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "InMemoryDatabase{" +
                "users=" + users +
                '}';
    }

    @Override
    public boolean register(User user) {
        if (this.contains(user)) {
            return false;
        } else {
            this.users.add(user);
            return true;
        }
    }

    private boolean contains(String userName) {
        try {
            return this.getUser(userName) != null;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    private boolean contains(User user){
        return this.contains(user.getUserName());
    }


    @Override
    public int getNumberUsers() {
        return this.users.size();
    }

    @Override
    public User getUser(String userName) {
        for (User u: this.users) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }
        throw new IllegalArgumentException(userName + " is not a registered user");
    }



    @Override
    public boolean authenticate(String userName, String password) {
        try {
            return this.getUser(userName).getPassword().equals(password);
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public ChatMessage addMessage(String userName, String message) {
        if(this.contains(userName)){
            lastid++;
            ChatMessage chatMessage = new ChatMessage( this.lastid , userName , message);
            this.messages.add(chatMessage);
            return chatMessage;
        } else {
            throw new IllegalArgumentException("This account does not existed");
        }
    }

    @Override
    public int getNumberMessages() {
        return this.messages.size();
    }

    @Override
    public List<ChatMessage> getRecentMessages(int n) {
        if (n > 0 && n < this.messages.size()){
            return messages.subList(this.messages.size()-n,this.messages.size());
        }
        else {
            return new LinkedList<>(messages);
        }
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String userName) {
        User user = this.getUser(userName);
        final int lastReadId = user.getLastReadId();
        if (lastReadId == this.lastid) {
            return new LinkedList<>();
        } else {
            int firstUnread = 0;
            for (ChatMessage m : this.messages) {
                firstUnread = m.getId();
                if (firstUnread > lastReadId) {
                    break;
                }
            }
            this.getUser(userName).setLastReadId(this.lastid);
            return this.messages.subList(firstUnread - 1, this.messages.size());
        }
    }


    @Override
    public void close() {
    }


}
