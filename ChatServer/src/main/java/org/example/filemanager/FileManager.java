package org.example.filemanager;

import org.example.ChatServer;
import org.example.users.User;
import org.example.util.ChatLogs;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileManager {
    private final ChatServer server;
    private final File folder = new File("config");
    private final File config = new File("ForbiddenWords.txt");

    public FileManager(ChatServer server){
        this.server = server;
        try{
            load();
            loadWords();
            startSaveTask();
        } catch (IOException e){
            System.out.println("Error while loading banned words!");
        }
    }

    public synchronized <T> void save(List<T> object, String filePath){
        File file = new File(filePath);
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;
        try{
            fileOut = new FileOutputStream(file);
            out = new ObjectOutputStream(fileOut);
            int size = object.size();
            out.write(size);
            for (T item : object){
                out.writeObject(item);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong while saving to file!");
        } finally {
            try{
                if (out != null){
                    out.flush();
                    out.close();
                }
                if (fileOut != null){
                    fileOut.flush();
                    fileOut.close();
                }
            }catch (Exception e){
                System.err.println("Error while closing saveFunction!");
                e.printStackTrace();
            }
        }
    }

    private void startSaveTask() {
        System.out.println("Initializing scheduler...");
        var scheduler = Executors.newScheduledThreadPool(1);


        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Executing save task...");
            saveBannedWords();
            save(server.getFileInfo().getChatLogs(), "config/chatLogs.txt");
            save(Arrays.asList(server.getUserManager().getUsers().toArray()), "config/Users.txt");
        }, 0, 3, TimeUnit.MINUTES);

        System.out.println("Scheduler started with a 3-minute interval.");
    }

    private void load(){
        FileInputStream usersIn = null;
        ObjectInputStream usersStream = null;
        FileInputStream chatLogIn = null;
        ObjectInputStream chatLogStream = null;
        try{
            usersIn = new FileInputStream(folder + "/Users.txt");
            usersStream = new ObjectInputStream(usersIn);
            int size = usersStream.read();
            if (!server.getUserManager().getUsers().isEmpty()){
                server.getUserManager().getUsers().clear();
            }
            for (int i = 0; i < size; i++){
                User user = (User) usersStream.readObject();
                server.getUserManager().addUser(user.getIdentifier(), user);
            }
            chatLogIn = new FileInputStream(folder + "/chatLogs.txt");
            chatLogStream = new ObjectInputStream(chatLogIn);
            size = chatLogStream.read();
            if (!server.getFileInfo().getChatLogs().isEmpty()){
                server.getFileInfo().getChatLogs().clear();
            }
            for (int i = 0; i < size; i++){
                String log = (String) chatLogStream.readObject();
                server.getFileInfo().getChatLogs().add(log);
            }
        }catch (Exception e){
            System.out.println("Something went wrong loading the files!");
            e.printStackTrace();
        } finally {
            try {
                if (usersStream != null) usersStream.close();
                if (usersIn != null) usersIn.close();
                if (chatLogStream != null) chatLogStream.close();
                if (chatLogIn != null) chatLogIn.close();
            } catch (IOException e){
                System.out.println("Something went wrong closing streams while loading.");
            }
        }
    }

    private void loadWords() throws IOException {
        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (!config.exists()){
            System.out.println("Can't find config!");
            config.createNewFile();
            return;
        }

        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(config));
            String line;
            while ((line = reader.readLine()) != null){
                server.getFileInfo().getBannedWords().add(line);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong loading from file!");
        } finally {
            try{
                if (!(reader == null)) reader.close();
            }catch (Exception e){
                System.out.println("Something went wrong closing the buffered reader!");
            }
        }
    }

    private void saveBannedWords(){


        if (!config.exists()){
            System.out.println("Cannot find config path!");
            return;
        }
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(config));
            for (String word : server.getFileInfo().getBannedWords()){
                writer.write(word + "\n");
                writer.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong saving to config!");
        } finally {
            try{
                if (!(writer == null)) writer.close();
            } catch (Exception e){
                System.out.println("Something went wrong closing the bufferedwriter!");
            }
        }
    }

}
