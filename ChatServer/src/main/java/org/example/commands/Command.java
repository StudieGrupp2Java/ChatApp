package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;

public abstract class Command {

    // Template method to handle common functionality
    public final void executeWithValidation(String[] args, ChatServer main, ConnectionHandler sender) throws CommandPermissionException {
        hasPermission(main, sender, getPermissionLevel());
        validateArgs(args, getExpectedArgsCount());
        execute(args, main, sender);
    }

    protected abstract void execute(String[] args, ChatServer main, ConnectionHandler sender);
    protected abstract int getExpectedArgsCount();

    protected void validateArgs(String[] args, int expectedArgs) {
        if (args.length < expectedArgs) {
            throw new IllegalArgumentException("Invalid arguments. Expected " + expectedArgs + " arguments.");
        }
    }

    public abstract ChatRole getPermissionLevel();

    protected void hasPermission(ChatServer main, ConnectionHandler sender, ChatRole expectedRole) throws CommandPermissionException {
        if (expectedRole == null || expectedRole.equals(ChatRole.NONE)) return;
        final User user = main.getUserManager().getUser(sender.getIdentifier());

        if (user == null) {
            throw new CommandPermissionException("No permission, expected '" + expectedRole + "' got 'NOT_AUTHENTICATED_USER'");
        }

        if (user.getRole().ordinal() < expectedRole.ordinal()) {
            throw new CommandPermissionException("No permission, expected '" + expectedRole + "' got '" + user.getRole() + "'");
        }
    }
}
