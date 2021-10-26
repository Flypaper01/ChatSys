package programming3.chatsys.data;

import java.util.Objects;

public class User {
    private String userName;
    private String fullName;
    private String password;
    private int lastReadId;

    public User(String userName, String fullName, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    public int getLastReadId() {
        return lastReadId;
    }

    public void setLastReadId(int lastReadId) {
        this.lastReadId = lastReadId;
    }
    public String format() {
        return this.userName + "\t" + this.fullName + "\t" + this.password + "\t" + this.lastReadId;
    }

    public void parse(String s) {
        String[] split = s.split("\t");
        if (split.length == 4) {
            this.userName = split[0];
            this.fullName = split[1];
            this.password = split[2];
            this.lastReadId = Integer.parseInt(split[3]);
        } else {
            throw new IllegalArgumentException("The length does not fit.");
        }
    }
}
