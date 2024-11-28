package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.util.Util;

import java.util.Optional;

public class KickUserCommand extends Command {
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        String username = args[0];

        final User senderUser = main.getUserManager().getUser(sender.getIdentifier());

        final Optional<User> user = main.getUserManager().getUser(username);

        user.ifPresent(value -> {
            ConnectionHandler connection = main.getClientManager().get(value.getIdentifier());
            if (connection != null) {
                connection.sendMessage("Kicked by " + Util.formatUserName(senderUser));
                main.getClientManager().removeConnection(connection);
            }
        });
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.ADMIN;
    }
}
