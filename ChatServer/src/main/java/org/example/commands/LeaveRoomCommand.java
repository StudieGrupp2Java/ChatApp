package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class LeaveRoomCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        main.getChatRoom().removeUserFromRoom(sender, main.getUserManager().getUser(sender.getIdentifier()).getCurrentRoom());
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
