package org.example.filter;

import org.example.util.FileInformationHandler;

import java.util.function.Predicate;

public class ChatFilter {

    private boolean useFilter;

    private Predicate<String> filter;

    private final FileInformationHandler fileInfo;
    public ChatFilter(FileInformationHandler fileInfo){
        this.fileInfo = fileInfo;
    }

    /**
     * Filters a message according to the current filter that is set for this server.
     * @param message to be filtered
     * @return the filtered message
     */
    public String filterMessage(String message) {
        filter = message::contains;
        for (String word : fileInfo.getBannedWords()){
            if (filter.test(word)){
                return message.replace(word, "*****");
            }
        }
        return message;
    }




}
