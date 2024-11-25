package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class ListUsersCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        sender.sendMessage("--List of members--");
        main.getUserManager().getUsers().forEach(user ->sender.sendMessage(user.getName()));
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
