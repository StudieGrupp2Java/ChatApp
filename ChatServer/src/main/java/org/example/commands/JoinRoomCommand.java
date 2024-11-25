package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class JoinRoomCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        main.getChatRoom().addUserToRoom(sender, args[0]);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
