package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;

public class ToggleMessageSoundCommand extends Command {
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        User user = main.getUserManager().getUser(sender.getIdentifier());
        user.getNotificationSettings().toggleMessageSounds(sender);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }

    @Override
    public String getUsage() {
        return "/togglemessagesound - toggles message sound notifications";
    }
}
