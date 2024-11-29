package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

public class ToggleDMSoundCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        User user = main.getUserManager().getUser(sender.getIdentifier());
        if (args[0].equalsIgnoreCase("on")){
            user.getNotificationSettings().setNotifyOnDM(true);
        } else if (args[0].equalsIgnoreCase("off")){
            user.getNotificationSettings().setNotifyOnDM(false);
        }
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
