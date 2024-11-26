package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class LeaveRoomCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        main.getChatRoomManager().switchRoom(sender, "Default");
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
