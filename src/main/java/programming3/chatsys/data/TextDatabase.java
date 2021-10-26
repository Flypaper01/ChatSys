package programming3.chatsys.data;

import java.io.*;
import java.lang.reflect.Field;

public class TextDatabase extends InMemoryDatabase {
    private File db_Message;
    private File db_User;

    public TextDatabase(File db_Message, File db_User) {
        this.db_Message = db_Message;
        this.db_User = db_User;
    }

    private void readUsers(){

    }

    private void readChatMessage(){

    }

    private void saveUsers() {
        try (FileWriter fileWriter = new FileWriter("database_User.txt",true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            for (User u : this.db_User)

        } catch (IOException e) {
           System.out.println("The users can't write");
        }
    }

    private void saveChatMessages(){

    }

    @Override
    public void close(){
        saveUsers();
        saveChatMessages();
        this.close();
    }


}
