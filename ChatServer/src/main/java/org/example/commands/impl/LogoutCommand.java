package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.util.Util;

public class LogoutCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        User user = main.getUserManager().getUser(sender.getIdentifier());

        if (user == null) {
            sender.sendMessage("You're not logged in.");
            return;
        }

        sender.sendMessage("Logging out...");
        main.getClientManager().logout(sender);
        sender.sendMessage("Bye!");

        user.setStatus(User.Status.OFFLINE);
        main.getClientManager().broadcastMessageInRoom(Util.formatUserName(user) + " logged out!", true, user);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.NONE;
    }

    @Override
    public String getUsage() {
        return "/logout - logs out of the server";
    }
}
