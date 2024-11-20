package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class LoginCommand extends Command {


    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        String username = args[0];
        String password = args[1];

        // Authentication logic
        System.out.println("Logging in user: " + username);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
