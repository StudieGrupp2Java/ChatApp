package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.ServerCommand;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

import java.util.Optional;

public class KickUserServerCommand extends ServerCommand {

    @Override
    protected void execute(String[] args, ChatServer main) {
        String username = args[0];

        final Optional<User> user = main.getUserManager().getUser(username);

        user.ifPresent(value -> {
            ConnectionHandler connection = main.getClientManager().get(value.getIdentifier());
            if (connection != null) {
                connection.sendMessage("Kicked by Server");
                main.getClientManager().removeConnection(connection);
            }
        });
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
