package org.example.filemanager;

import org.example.util.ChatLogs;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileManager {
    private final ChatLogs fileInfo;
    private final File folder = new File("config");
    private final File config = new File("ForbiddenWords.txt");

    public FileManager(ChatLogs fileInfo){
        this.fileInfo = fileInfo;
        try{
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
            save(fileInfo.getChatLogs(), folder + "/"+ config);
        }, 0, 3, TimeUnit.MINUTES);

        System.out.println("Scheduler started with a 3-minute interval.");
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
            reader = new BufferedReader(new FileReader(folder + "/" + config));
            String line;
            while ((line = reader.readLine()) != null){
                fileInfo.getBannedWords().add(line);
            }
        }catch (Exception e) {
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
            for (String word : fileInfo.getBannedWords()){
                writer.write(word + "\n");
                writer.flush();
            }
        }catch (Exception e){
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
