package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;

public class ToggleDMSoundCommand extends Command {
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

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }
}
