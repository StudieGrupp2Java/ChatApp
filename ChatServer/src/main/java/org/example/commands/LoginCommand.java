package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

import java.util.Optional;

public class LoginCommand extends Command {


    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        if (sender.isLoggedIn()) {
            sender.sendMessage("You are already logged in.");
            return;
        }

        String username = args[0];
        String password = args[1];

        // Authentication logic
        System.out.println("Logging in user: " + username);

        final Optional<User> userOptional = main.getUserManager().getUser(username);
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(password)) {
            sender.sendMessage("Invalid username or password.");
            return;
        }

        // Successfully authenticated
        User user = userOptional.get();

        if (main.getClientManager().isConnected(user.getIdentifier())) {
            sender.sendMessage("This user is already logged in.");
            return;
        }

        main.getClientManager().login(sender, user);

        sender.sendMessage("Welcome " + user.getName() + "!");
        main.getClientManager().broadcastMessage(user.getName() + " logged in!", true);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
