package org.example.util;

import org.example.handling.ConnectionHandler;
import org.example.users.User;

import java.net.CookieHandler;

public class NotificationManager {

    // Check whether notification sound should be played at new message
    public boolean shouldNotifyForMessage(User user){
        return user.getNotificationSettings().isNotifyOnMessage();
    }

    // Check whether notification sound should be played at new DM
    public boolean shouldNotifyForDM(User user){
        return user.getNotificationSettings().isNotifyOnDM();
    }

    // Handle notifications, e.g. send commands to client
    public void sendNotification(ConnectionHandler handler, String notificationType){
        handler.sendMessage("/playSound " + notificationType); // e.g. "/playSound message" eller "/playSound dm"
    }
}
