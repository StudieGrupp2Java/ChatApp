package org.example.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatLogs implements Serializable {
    private final List<String> bannedWords = new ArrayList<>();
    private final List<String> chatLog = new ArrayList<>();
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");


    public synchronized void addMessage(String username, String message) {
        String logEntry = String.format("[%s] %s: %s", format.format(System.currentTimeMillis()), username, message);
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
