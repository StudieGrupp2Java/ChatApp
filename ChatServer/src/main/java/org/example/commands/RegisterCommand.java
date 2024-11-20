package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

public class RegisterCommand extends Command {

    public RegisterCommand(ChatServer main, ConnectionHandler sender) {
        super(main, sender);
    }

    @Override
    public void execute(String[] args) {
        String username = args[0];
        String password = args[1];

        System.out.println("Registering user: " + username);

        boolean exists = main.getUserManager().userExists(username);

        if (username.isEmpty()){
            sender.sendMessage("Username cannot be empty!");
            return;
        }
        if (exists) {
            sender.sendMessage("User " + username + " already exists. Choose another username.");
            return;
        }

        main.getUserManager().addUser(sender.getIdentifier(), new User(sender.getIdentifier(), username, password));
        sender.sendMessage("Successfully registered.");
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
