package programming3.chatsys.data;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDatabaseTest {
    private InMemoryDatabase db;

    @org.junit.jupiter.api.BeforeEach
     void setUp() {
        db = new InMemoryDatabase();
        db.register(new User("zhang","ZhangZeyang","password"));
        db.addMessage("zhang","message1");
        db.addMessage("zhang","message2");
        db.addMessage("zhang","message3");

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        db.close();

    }

    @org.junit.jupiter.api.Test
    void testAuthenticate() {
        assertTrue(db.authenticate("zhang","password"));
        assertFalse(db.authenticate("zhang","not"));
        assertFalse(db.authenticate("wang","password"));
    }
    @org.junit.jupiter.api.Test
    void testNewdatabase(){
        assertEquals(1,db.getNumberUsers());
        assertEquals(3,db.getNumberMessages());
    }
    @org.junit.jupiter.api.Test
    void testGetRecentMessages(){
        System.out.println(db.getRecentMessages(1));
        System.out.println(db.getRecentMessages(5));

    }
    @org.junit.jupiter.api.Test
    void testRegister(){
            assertFalse(db.register(new User("zhang","ZhangZeyang","password")));
            assertTrue(db.register(new User("flypaper","Flypaper","pass")));
            assertEquals(2,db.getNumberUsers());
            assertTrue(db.register(new User("Zhang","ZhangZeyang","password")));
            assertEquals(3,db.getNumberUsers());
    }
    @org.junit.jupiter.api.Test
    void testAddMessage(){
        System.out.println(db.addMessage("zhang","message3"));
        System.out.println(db.addMessage("zhang","message4"));

    }


}