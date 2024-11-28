package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.ServerCommand;
import org.example.users.ChatRole;
import org.example.users.User;

import java.util.Optional;

public class CreateAdminServerCommand extends ServerCommand {

    @Override
    protected void execute(String[] args, ChatServer main) {
        String username = args[0];

        final Optional<User> user = main.getUserManager().getUser(username);

        user.ifPresent(value -> {
            value.setRole(ChatRole.ADMIN);
            System.out.println("Set " + value.getName() + "'s role to " + value.getRole() + "!");

        });
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
