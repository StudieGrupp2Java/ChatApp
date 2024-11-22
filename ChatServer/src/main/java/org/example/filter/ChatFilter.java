package org.example.filter;

import org.example.util.ChatLogs;

import java.util.function.Predicate;

public class ChatFilter {

    private boolean useFilter;

    private Predicate<String> filter;

    private final ChatLogs chatInfo;
    public ChatFilter(ChatLogs chatInfo){
        this.chatInfo = chatInfo;
    }

    /**
     * Filters a message according to the current filter that is set for this server.
     * @param message to be filtered
     * @return the filtered message
     */
    public String filterMessage(String message) {
        filter = message.toLowerCase()::contains;
        for (String word : chatInfo.getBannedWords()){
            if (filter.test(word.toLowerCase())){
                message = message.replaceAll("(?i)" + word, "*".repeat(word.length()));
            }
        }
        return message;
    }




}
