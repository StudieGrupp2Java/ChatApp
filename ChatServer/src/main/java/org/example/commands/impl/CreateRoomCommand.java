package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;

public class CreateRoomCommand extends Command {
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        if (args[0].isBlank()) {
            sender.sendMessage("Need to enter a name for the room!");
            return;
        }
        main.getChatRoomManager().createRoom(args[0], sender);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }
}
