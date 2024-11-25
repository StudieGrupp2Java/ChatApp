package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class OnlineCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        main.getUserManager().getUsers()
                .forEach(user -> {

                });
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
