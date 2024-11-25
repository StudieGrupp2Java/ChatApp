package org.example.util;

public class Util {

    public static String timeSince(long lastSeen) {
        long diff = System.currentTimeMillis() - lastSeen;
        return TimeAgo.toDuration(diff) + " ago";
    }
}
