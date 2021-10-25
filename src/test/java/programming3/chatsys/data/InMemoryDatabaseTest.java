package programming3.chatsys.data;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDatabaseTest {
    private InMemoryDatabase db;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        db = new InMemoryDatabase();
        db.register(new User("zhang","ZhangZeyang","password"));
        db.addMessage("Zhang","message1");
        db.addMessage("Zhang","message2");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        db.close();

    }

    @org.junit.jupiter.api.Test
    void testauthenticate() {
        assertTrue(db.authenticate("zhang","password"));
        assertFalse(db.authenticate("zhang","not"));
        assertFalse(db.authenticate("wang","password"));
    }
    @org.junit.jupiter.api.Test
    void testnewdatabase(){
        assertEquals(1,db.getNumberUsers());
        assertEquals(1,db.getNumberMessages());
    }
    @org.junit.jupiter.api.Test
    void testGetRecentMessages(){
        assertEquals("message2",db.getRecentMessages(1));
        assertNull(db.getRecentMessages(10));

    }
    @org.junit.jupiter.api.Test
    void testRegister(){
            assertFalse(db.register(new User("zhang","ZhangZeyang","password")));
            assertTrue(db.register(new User("flypaper","Flypaper","pass")));
            assertEquals(2,db.getNumberUsers());
            assertFalse(db.register(new User("Zhang","ZhangZeyang","password")));
            assertEquals(2,db.getNumberUsers());
    }
    @org.junit.jupiter.api.Test
    void testAddMessage(){


    }


}