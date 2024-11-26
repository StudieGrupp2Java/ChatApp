package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

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

        final User user = new User(username, password);

        main.getUserManager().addUser(user.getIdentifier(), user);
        main.getClientManager().login(sender, user);
        sender.sendMessage("Successfully registered.");

        sender.sendMessage("Welcome " + user.getName() + "!");
        main.getClientManager().broadcastMessage(user.getName() + " logged in for the first time! Say hi!", true);

        user.setCurrentRoom("Default");
        main.getChatRoom().addUserToRoom(sender, user.getCurrentRoom());
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
