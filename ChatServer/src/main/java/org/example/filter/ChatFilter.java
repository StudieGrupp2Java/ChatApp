package org.example.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ChatFilter implements Serializable {

    private boolean useFilter;

    private Predicate<String> filter;
    private final List<String> bannedWords = new ArrayList<>();


    /**
     * Filters a message according to the current filter that is set for this server.
     * @param message to be filtered
     * @return the filtered message
     */
    public String filterMessage(String message) {
        filter = message.toLowerCase()::contains;
        for (String word : this.getBannedWords()){
            if (filter.test(word.toLowerCase())){
                message = message.replaceAll("(?i)" + word, "*".repeat(word.length()));
            }
        }
        return message;
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
