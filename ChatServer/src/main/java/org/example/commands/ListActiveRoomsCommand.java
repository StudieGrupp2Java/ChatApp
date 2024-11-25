package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class ListActiveRoomsCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        if (main.getChatRoom().getChatRooms().isEmpty()){
            sender.sendMessage("No rooms have been created. /createroom <room name> to create a new room!");
            return;
        }
        sender.sendMessage("--Current rooms--");
        for (String room : main.getChatRoom().getRoomList()){
            int roomSize = main.getChatRoom().getChatRooms().get(room).size();
            sender.sendMessage("Room " + room + " currently has " + roomSize + " online members!");
        }

        sender.sendMessage("To join a room type /join <room name>");

    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
