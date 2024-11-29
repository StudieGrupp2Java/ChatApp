package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;

public class DMCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {

        String recipient = args[0];
        String message = args[1];

        // TODO: Lägg till logik för att skicka ett DM
        System.out.println("Sending DM to " + recipient + ": " + message);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }
}