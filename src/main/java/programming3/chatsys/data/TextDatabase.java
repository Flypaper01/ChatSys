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
        if (this.db_User.exists()){
            try(FileReader filereader = new FileReader("database_User");
                BufferedReader bufferedreader = new BufferedReader(filereader);) {
                while (true){
                    String line = bufferedreader.readLine();
                    if (line == null) {
                        break;
                    } else {
                        User u = new User(line);
                        this.users.add(u);
                    }
                }

            }catch (IOException e){
                System.out.println("");
            }

        }

    }

    private void readChatMessage(){

    }

    private void saveUsers() {
        try (FileWriter fileWriter = new FileWriter("database_User.txt",true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            for (User u : this.users){
                bufferedWriter.write(User.format());
            }
            bufferedWriter.flush();
        } catch (IOException e) {
           System.out.println("The users can't write");
        }
    }

    private void saveChatMessages(){
        try (FileWriter fileWriter = new FileWriter("database_Message.txt",true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("The message can't write");
        }
    }

    @Override
    public void close(){
        saveUsers();
        saveChatMessages();
        super.close();
    }


}
