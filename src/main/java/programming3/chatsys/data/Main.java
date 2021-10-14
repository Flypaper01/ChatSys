package programming3.chatsys.data;


public class Main {
    public static void main(String[] args) {
        User user1 = new User("johndoe", "John Doe", "thepassword");
        User user2 = new User("jane", "Jane Doe", "janespassword");
        User user3 = new User("johndoe", "John Doe", "thepassword");
        User user4 = new User("johndoe", "John Doe", "anotherpassword");
        User user5 = new User("johndoe", "John Doe 2", "anotherpassword");
        User user6 = user1;
//        System.out.println(user1);
//        System.out.println(user2);
//        System.out.println(user3);
//        System.out.println(user4);
//        System.out.println(user5);
//        System.out.println(user6);
//
//        System.out.println(user1 == user6);
//        System.out.println(user1 == user3);
//        System.out.println(user1.equals(user3));
//        System.out.println(user1.equals(user2));
//        System.out.println(user1.equals(user4));
//        System.out.println(user1.equals(user5));
//        System.out.println(user1.equals(user6));

        Database db = new InMemoryDatabase();
        System.out.println(db);
        System.out.println(db.register(user1));
        System.out.println(db.register(user2));
        System.out.println(db.register(user3));
        System.out.println(db.register(user4));
        System.out.println(db.register(user5));
        System.out.println(db.register(user6));
        System.out.println(db);

        System.out.println(db.getNumberUsers());

        System.out.println(db.getUser("johndoe"));
        System.out.println(db.getUser("jane"));
        try {
            System.out.println(db.getUser("john"));
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(db.authenticate("johndoe", "thepassword"));
        System.out.println(db.authenticate("johndoe", "wrongpassword"));
        System.out.println(db.authenticate("john", "thepassword"));
    }
}
