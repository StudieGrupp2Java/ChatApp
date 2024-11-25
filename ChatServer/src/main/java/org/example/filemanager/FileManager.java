package org.example.filemanager;

import org.example.ChatServer;
import org.example.users.User;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileManager {
    private final ChatServer server;
    private final String FOLDER = "config";
    private String WORDS;
    private String CHATLOGS;
    private String USERS;
    private final String FILEPATHS = "configPaths.txt";

    public FileManager(ChatServer server){
        this.server = server;
        try{
            loadPaths();
            load();
            loadWords();
            startSaveTask();
        } catch (IOException e) {
            System.out.println("Error while loading banned words!");
        }
    }

    private void loadPaths(){
        File paths = new File(FOLDER + "/" + FILEPATHS);
        if (!paths.exists()){
            CHATLOGS = "chatLogs.txt";
            WORDS = "ForbiddenWords.txt";
            USERS = "Users.txt";
            return;
        }
        BufferedReader pathReader = null;
        try {
            pathReader = new BufferedReader(new FileReader(paths));
            String path;
            while((path = pathReader.readLine()) != null){
                String[] splitPath = path.split("=");
                if (splitPath[0].equalsIgnoreCase("ChatLogs")){
                    CHATLOGS = splitPath[1];
                } else if (splitPath[0].equalsIgnoreCase("BannedWords")){
                    WORDS = splitPath[1];
                } else if (splitPath[0].equalsIgnoreCase("Users")) {
                    USERS = splitPath[1];
                }
            }
        } catch (IOException e){
            System.out.println("Something went wrong loading file paths");
        } finally {
            try {
                if (pathReader != null) pathReader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public synchronized <T> void save(List<T> object, String filePath){
        if (object.isEmpty()) return;
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

    public void saveAll() {
        System.out.println("Executing save task...");
        saveBannedWords();
        save(server.getChatInfo().getChatLogs(), FOLDER + "/" + CHATLOGS);
        save(Arrays.asList(server.getUserManager().getUsers().toArray()), FOLDER + "/" + USERS);
    }

    private void startSaveTask() {
        System.out.println("Initializing scheduler...");
        var scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(this::saveAll, 0, 3, TimeUnit.MINUTES);

        System.out.println("Scheduler started with a 3-minute interval.");
    }

    private void load(){
        FileInputStream usersIn = null;
        ObjectInputStream usersStream = null;
        FileInputStream chatLogIn = null;
        ObjectInputStream chatLogStream = null;
        try{
            usersIn = new FileInputStream(FOLDER + "/" + USERS);
            usersStream = new ObjectInputStream(usersIn);
            int size = usersStream.read();
            if (!server.getUserManager().getUsers().isEmpty()){
                server.getUserManager().getUsers().clear();
            }
            for (int i = 0; i < size; i++){
                User user = (User) usersStream.readObject();
                user.setInitalStatus(User.Status.OFFLINE);
                server.getUserManager().loadUser(user.getIdentifier(), user);
            }
            chatLogIn = new FileInputStream(FOLDER + "/" + CHATLOGS);
            chatLogStream = new ObjectInputStream(chatLogIn);
            size = chatLogStream.read();
            if (!server.getChatInfo().getChatLogs().isEmpty()){
                server.getChatInfo().getChatLogs().clear();
            }
            for (int i = 0; i < size; i++){
                String log = (String) chatLogStream.readObject();
                server.getChatInfo().getChatLogs().add(log);
            }
        }catch (Exception e){
            System.out.println("Something went wrong loading the files!");
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
        File folder = new File(FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File words = new File(FOLDER + "/" + WORDS);
        if (!words.exists()){
            System.out.println("Can't find config!");
            words.createNewFile();
            return;
        }

        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(words));
            String line;
            while ((line = reader.readLine()) != null){
                server.getChatInfo().getBannedWords().add(line);
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
        File words = new File(FOLDER + "/" + WORDS);
        if (!words.exists()){
            System.out.println("Cannot find config path!");
            return;
        }
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(words));
            for (String word : server.getChatInfo().getBannedWords()){
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
