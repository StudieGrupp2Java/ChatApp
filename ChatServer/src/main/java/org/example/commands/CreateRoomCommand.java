package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class CreateRoomCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        main.getChatRoom().createRoom(args[0], sender);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
