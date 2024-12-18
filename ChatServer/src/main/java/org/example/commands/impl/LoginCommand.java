package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.util.Util;

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
        if (userOptional.isEmpty()) {
            sender.sendMessage("Invalid username or password.");
            return;
        }

        if (!userOptional.get().passwordMatches(password)) {
            sender.sendMessage("Invalid username or password.");
            return;
        }

        // Successfully authenticated
        User user = userOptional.get();

        if (main.getClientManager().isConnected(user.getIdentifier())) {
            sender.sendMessage("This user is already logged in.");
            return;
        }

        sender.sendMessage("Welcome " + Util.formatUserName(user) + "!");
        main.getClientManager().login(sender, user);

        main.getClientManager().broadcastMessageInRoom(Util.formatUserName(user) + " logged in!", true, user);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.NONE;
    }

    @Override
    public String getUsage() {
        return "/login <username> <password> - logs in to the server";
    }
}
