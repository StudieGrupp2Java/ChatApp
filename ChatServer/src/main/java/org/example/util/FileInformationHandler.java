package org.example.util;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInformationHandler {
    private final List<String> bannedWords = new ArrayList<>();
    private final List<String> chatLog = new ArrayList<>();

    public FileInformationHandler(){

    }


    public synchronized void addMessage(String username, String message) {
        String logEntry = String.format("[%s] %s: %s", LocalDateTime.now(), username, message);
        chatLog.add(logEntry);
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

    public List<String> getChatLogs(){
        return chatLog;
    }
}
