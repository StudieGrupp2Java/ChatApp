package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

public class JoinRoomCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        if (args[0].isBlank()){
            sender.sendMessage("Need to input a room name!");
            return;
        }
        User user = main.getUserManager().getUser(sender.getIdentifier());
        if (!args[0].equals(user.getCurrentRoom())){
            main.getChatRoomManager().switchRoom(sender, args[0]);
            return;
        }
        main.getChatRoomManager().addUserToRoom(sender, args[0]);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
