package org.example.users;

import org.example.handling.ConnectionHandler;

import java.io.Serializable;

public class UserNotificationSettings implements Serializable {
    private boolean notifyOnMessage;
    private boolean notifyOnDM;

    public UserNotificationSettings(){
        this.notifyOnMessage = true;
        this.notifyOnDM = true;
    }

    public boolean isNotifyOnMessage() {
        return notifyOnMessage;
    }

    public void toggleMessageSounds(ConnectionHandler sender) {
        if (notifyOnMessage) {
            sender.sendMessage("Toggled dm sounds off");
        } else {
            sender.sendMessage("Toggled dm sounds on");
        }

        notifyOnMessage = !notifyOnMessage;
    }

    public boolean isNotifyOnDM() {
        return notifyOnDM;
    }

    public void toggleDmSounds(ConnectionHandler sender) {
        if (notifyOnDM) {
            sender.sendMessage("Toggled dm sounds off");
        } else {
            sender.sendMessage("Toggled dm sounds on");
        }

        notifyOnDM = !notifyOnDM;
    }
}
