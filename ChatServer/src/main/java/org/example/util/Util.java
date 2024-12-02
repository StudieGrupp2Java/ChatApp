package org.example.util;

import org.example.users.ChatRole;
import org.example.users.User;

import java.text.SimpleDateFormat;

public class Util {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");


    public static String timeSince(long lastSeen) {
        long diff = System.currentTimeMillis() - lastSeen;
        return TimeAgo.toDuration(diff) + " ago";
    }

    public static String formatUserName(User user) {
        return colorFromRole(user.getRole()) + user.getName() + TextColor.RESET;
    }

    private static String colorFromRole(ChatRole role) {
        switch (role) {
            case USER -> {
                return TextColor.GREEN;
            }
            case ADMIN -> {
                return TextColor.BOLD + TextColor.RED;
            }
        }
        return "";
    }
}
