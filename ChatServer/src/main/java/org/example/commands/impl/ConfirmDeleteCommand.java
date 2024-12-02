package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.users.UserManager;

public class ConfirmDeleteCommand extends Command {
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        UserManager userManager = main.getUserManager();
        User user = userManager.getUser(sender.getIdentifier());

        // Check if user is logged in
        if (user == null){
            sender.sendMessage("You're not logged in, and cannot confirm account deletion.");
            return;
        }

        // Check if account is flagged as pending deletion
        if (!user.isPendingDeletion()){
            sender.sendMessage("Your account is not marked for deletion. First, write /deleteaccount to start deleting your account.");
            return;
        }

        // Delete account from UserManager
        userManager.removeUser(sender.getIdentifier());
        main.getClientManager().logout(sender);
        sender.sendMessage("Your account has been permanently deleted.");

        // TODO: maybe we should log this kind of stuff
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }
}
