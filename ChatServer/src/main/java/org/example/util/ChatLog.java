package org.example.util;

import java.io.Serializable;

public class ChatLog implements Serializable {
    private final long timestamp;
    private final String message;

    public ChatLog(long timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
