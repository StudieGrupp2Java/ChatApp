package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;

public class JoinRoomCommand extends Command {
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

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }

    @Override
    public String getUsage() {
        return "/join <room> - joins an existing room. Do /rooms to see what rooms exist or /create to create a new room";
    }
}
