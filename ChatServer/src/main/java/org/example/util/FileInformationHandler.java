package org.example.util;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileInformationHandler {
    private final List<String> bannedWords = new ArrayList<>();
    private final File folder = new File("config");
    private final File config = new File(folder + "/ForbiddenWords.txt");

    public FileInformationHandler(){
        try {
            loadWords();
        } catch (IOException e) {
            System.err.println("Couldn't load words");
        }
    }

    public void loadWords() throws IOException {
        if (!folder.exists()) {
            Files.createDirectories(folder.toPath());
        }

        if (!config.exists()){
            System.out.println("Can't find config!");
            Files.createFile(config.toPath());
            return;
        }

        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(config));
            String line;
            while ((line = reader.readLine()) != null){
                bannedWords.add(line);
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

    public void saveBannedWords(){
        if (!config.exists()){
            System.out.println("Cannot find config path!");
            return;
        }
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(config));
            for (String word : bannedWords){
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

    public List<String> getBannedWords(){
        return bannedWords;
    }

    public void addBannedWord(String word){
        bannedWords.add(word);
    }

    public void removeBannedWord(String word){
        bannedWords.remove(word);
    }
}
