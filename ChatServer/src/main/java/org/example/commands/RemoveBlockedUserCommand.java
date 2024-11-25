package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

public class RemoveBlockedUserCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        User user = main.getUserManager().getUser(sender.getIdentifier());
        main.getUserManager().getUsers().stream().map(User::getName).forEach(name -> {
            if (name.equals(args[0])){
                user.removeBlockedUser(name, sender);
                sender.sendMessage("Removed " + name + " from blocked list!");
            }
        });
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
