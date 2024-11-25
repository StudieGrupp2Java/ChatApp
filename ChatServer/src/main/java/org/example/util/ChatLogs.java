package org.example.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatLogs implements Serializable {
    private final List<String> bannedWords = new ArrayList<>();
    private final List<ChatLog> chatLog = new ArrayList<>();

    public synchronized void addMessage(String logEntry) {
        chatLog.add(new ChatLog(System.currentTimeMillis(), logEntry));
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

    public List<ChatLog> getChatLogs(){
        return chatLog;
    }
}
