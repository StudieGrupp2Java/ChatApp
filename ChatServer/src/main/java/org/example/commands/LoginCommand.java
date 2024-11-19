package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class LoginCommand extends Command {

    public LoginCommand(ChatServer main, ConnectionHandler sender) {
        super(main, sender);
    }

    @Override
    protected void execute(String[] args) {
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
