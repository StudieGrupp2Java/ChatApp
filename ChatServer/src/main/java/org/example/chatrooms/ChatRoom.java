package org.example.chatrooms;

import lombok.Getter;
import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;
import org.example.util.ChatLog;
import org.example.util.Util;

import java.util.*;

public class ChatRoom {
    @Getter
    private final Map<String, List<ConnectionHandler>> chatRooms = new HashMap<>();
    @Getter
    private final Map<String, List<ChatLog>> chatRoomLogs = new HashMap<>();
    private final ChatServer main;
    public ChatRoom(ChatServer main){
        this.main = main;
        chatRooms.put("Default", new ArrayList<>());
    }

    public void createRoom(String roomName, ConnectionHandler sender) {
        chatRooms.putIfAbsent(roomName, new ArrayList<>());
        sender.sendMessage("Created room: " + roomName);
        chatRoomLogs.put(roomName, new ArrayList<>());
    }

    // Returns true if the room exists
    public boolean addUserToRoom(ConnectionHandler client, String roomName) {
        if (!chatRooms.containsKey(roomName)){
            client.sendMessage("No room with that name exists!");
            return false;
        }
        if (chatRooms.get(roomName).contains(client)) return true;
        chatRooms.get(roomName).add(client);
        main.getUserManager().getUser(client.getIdentifier()).setCurrentRoom(roomName);
        client.sendMessage("Joined room: " + roomName);
        User user = main.getUserManager().getUser(client.getIdentifier());
        main.getClientManager().broadcastMessageInRoom(user.getName() + " joined the chat!", true, user);
        if (chatRoomLogs.containsKey(user.getCurrentRoom()) && !chatRoomLogs.get(user.getCurrentRoom()).isEmpty()) {
            chatRoomLogs.get(user.getCurrentRoom()).stream()
                    .sorted(Comparator.comparingLong(ChatLog::getTimestamp).reversed())
                    .limit(30)
                    .sorted(Comparator.comparingLong(ChatLog::getTimestamp))
                    .map(log -> String.format("[%s] %s", Util.DATE_FORMAT.format(log.getTimestamp()), log.getMessage()))
                    .forEach(client::sendMessage);
        }
        return true;
    }

    public void switchRoom(ConnectionHandler client, String roomName) {
        User user = main.getUserManager().getUser(client.getIdentifier());
        List<ConnectionHandler> room = chatRooms.get(user.getCurrentRoom());
        if (room != null) {
            room.remove(client);
        }
        String lastRoom = user.getCurrentRoom();

        if (!addUserToRoom(client, roomName)) {
            addUserToRoom(client, "Default");
            client.sendMessage("No room with that name exists!");
            return;
        }
        client.sendMessage("Switched from " + lastRoom + " to " + roomName);
    }

    public void removeUserFromRoom(ConnectionHandler client, String roomName) {
        List<ConnectionHandler> room = chatRooms.get(roomName);
        if (room != null) {
            room.remove(client);
        }
        client.sendMessage("Left room: " + roomName);
        User user = main.getUserManager().getUser(client.getIdentifier());
        main.getClientManager().broadcastMessageInRoom(user.getName() + " left the chat!", true, user);
        // Join default room

    }

    public List<String> getRoomList() {
        return new ArrayList<>(chatRooms.keySet());
    }

    public void addToChatLog(String roomName, String message){
        if (chatRoomLogs.containsKey(roomName)){
            chatRoomLogs.get(roomName).add(new ChatLog(System.currentTimeMillis(), message));
        }
    }

}
