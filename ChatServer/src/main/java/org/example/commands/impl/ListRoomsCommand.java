package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.util.TextColor;

import java.util.List;
import java.util.Objects;

public class ListRoomsCommand extends Command {
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        if (main.getChatRoomManager().getChatRooms().isEmpty()) {
            sender.sendMessage("No rooms have been created. /create <room name> to create a new room!");
            return;
        }
        int peopleInRoom = 0;
        sender.sendMessage("--Current rooms--");
        for (String room : main.getChatRoomManager().getRoomList()) {
            List<ConnectionHandler> roomUsers = main.getChatRoomManager().getUsersIn(room);
            int roomSize = roomUsers.size();
            for (ConnectionHandler handler : roomUsers) {
                User user = main.getUserManager().getUser(handler.getIdentifier());
                if (user == null) continue;
                if (user.getStatus().equals(User.Status.ONLINE) || user.getStatus().equals(User.Status.AWAY))
                    peopleInRoom++;
            }
            sender.sendMessage("Room '" + room + "' currently has " + roomSize + " members, " + peopleInRoom +  " of which are online");
            sender.sendMessage(getStatusForRoom(roomUsers, main));
            peopleInRoom = 0;
        }

        sender.sendMessage("To join a room type /join <room name>");

    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }

    private String getStatusForRoom(List<ConnectionHandler> room, ChatServer main) {
        StringBuilder builder = new StringBuilder("USERS: ");
        room.stream()
                .map(con -> main.getUserManager().getUser(con.getIdentifier()))
                .filter(Objects::nonNull) //TODO: offline users are currently null. Should change ChatRooms to hold Users instead of ConnectionHandlers.
                .forEach(user -> {
                    if (user.getStatus().equals(User.Status.ONLINE)) {
                        builder.append(TextColor.GREEN).append(user.getName()).append(TextColor.RESET).append(", ");
                    } else if (user.getStatus().equals(User.Status.AWAY)) {
                        builder.append(TextColor.WHITE).append(user.getName()).append(TextColor.RESET).append(", ");
                    } else if (user.getStatus().equals(User.Status.OFFLINE)) {
                        builder.append(TextColor.RED).append(user.getName()).append(TextColor.RESET).append(", ");
                    }
                });

        // Remove last comma
        if (builder.length() > "USERS: ".length()) {
            builder.setLength(builder.length() - 2);
        }
        return builder.toString();
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }

    @Override
    public String getUsage() {
        return "/rooms - lists available rooms and their users";
    }
}
