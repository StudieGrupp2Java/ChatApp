package org.example.users;

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

    public void setNotifyOnMessage(boolean notifyOnMessage) {
        this.notifyOnMessage = notifyOnMessage;
    }

    public boolean isNotifyOnDM() {
        return notifyOnDM;
    }

    public void setNotifyOnDM(boolean notifyOnDM) {
        this.notifyOnDM = notifyOnDM;
    }
}
