package org.example.filter;

import java.util.function.Predicate;

public class ChatFilter {

    private boolean useFilter;

    private Predicate<?> filter;

    /**
     * Filters a message according to the current filter that is set for this server.
     * @param message to be filtered
     * @return the filtered message
     */
    public String filterMessage(String message) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
