package org.example.util;

import java.text.SimpleDateFormat;

public class Util {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");


    public static String timeSince(long lastSeen) {
        long diff = System.currentTimeMillis() - lastSeen;
        return TimeAgo.toDuration(diff) + " ago";
    }
}
