package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.util.Util;

public class RegisterCommand extends Command {

    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        if (main.getUserManager().getUser(sender.getIdentifier()) != null) {
            sender.sendMessage("You can't register while logged in. Do /logout");
            return;
        }

        String username = args[0];
        String password = args[1];

        System.out.println("Registering user: " + username);

        boolean exists = main.getUserManager().userExists(username);

        if (username.isEmpty()) {
            sender.sendMessage("Username cannot be empty!");
            return;
        }
        if (exists) {
            sender.sendMessage("User " + username + " already exists. Choose another username.");
            return;
        }

        User user = new User(username, password);
        sender.sendMessage("Successfully registered.");
        sender.sendMessage("Welcome " + Util.formatUserName(user) + "!");

        main.getUserManager().addUser(user.getIdentifier(), user);
        main.getClientManager().login(sender, user);

        user.setCurrentRoom("Default");
        main.getClientManager().broadcastMessageInRoom(Util.formatUserName(user) + " logged in for the first time! Say hi!", true, user);
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
        return "/register <username> <password> - registers a new user and logs in";
    }
}
