package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

public class ListBlockedUsersCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        User user = main.getUserManager().getUser(sender.getIdentifier());
        sender.sendMessage( "\u001B[31m" + "--Blocked Members--");
        user.getBlockedUsers().forEach(name -> sender.sendMessage("\u001B[31m" + name));
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
