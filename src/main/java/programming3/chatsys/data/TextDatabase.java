package programming3.chatsys.data;

import java.io.*;
import java.lang.reflect.Field;

public class TextDatabase extends InMemoryDatabase {
    private File db_Message;
    private File db_User;

    public TextDatabase(File db_Message, File db_User) {
        this.db_Message = db_Message;
        this.db_User = db_User;

        this.loadUsers();
        this.loadMessage();
    }

    private void loadUsers(){
        if (this.db_User.exists()){
            try(FileReader filereader = new FileReader("database_User");
                BufferedReader bufferedreader = new BufferedReader(filereader)) {
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
                System.out.println("read failed");
            }

        }

    }

    private void loadMessage(){
        if(this.db_Message.exists()){
            try(FileReader filereader = new FileReader("database_Message");
                BufferedReader bufferedReader = new BufferedReader(filereader)){
                while (true){
                    String line = bufferedReader.readLine();
                    if (line == null){
                        break;
                    } else {
                        ChatMessage chatMessage = new ChatMessage(line);
                        this.messages.add(chatMessage);
                    }
                }

            }catch (IOException e){
                System.out.println("read failed");
            }
        }

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
            for (ChatMessage chatMessage : this.messages)
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
