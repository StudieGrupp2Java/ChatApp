package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;

public class BlockUserCommand extends Command {
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        User user = main.getUserManager().getUser(sender.getIdentifier());
        main.getUserManager().getUsers().stream().map(User::getName).forEach(name -> {
            if (name.equals(args[0])){
                user.addBlockedUser(name, sender);
                sender.sendMessage("Added " + name + " to blocked list!");
            }
        });
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }

    @Override
    public String getUsage() {
        return "/block <username> - blocks a users messages";
    }
}
