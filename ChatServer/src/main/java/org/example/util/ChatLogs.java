package org.example.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatLogs implements Serializable {
    private final List<String> bannedWords = new ArrayList<>();
    private final List<String> chatLog = new ArrayList<>();

    public synchronized void addMessage(String logEntry) {
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
