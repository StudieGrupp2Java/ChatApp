package org.example.chatrooms;

import lombok.Getter;
import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

import java.util.*;

public class ChatRoom {
    @Getter
    private final Map<String, List<ConnectionHandler>> chatRooms = new HashMap<>();
    private final ChatServer main;
    public ChatRoom(ChatServer main){
        this.main = main;
    }

    public void createRoom(String roomName, ConnectionHandler sender) {
        chatRooms.putIfAbsent(roomName, new ArrayList<>());
        sender.sendMessage("Created room: " + roomName);
    }

    public void addUserToRoom(ConnectionHandler client, String roomName) {
        if (chatRooms.containsKey(roomName)){
            chatRooms.get(roomName).add(client);
        }
        //chatRooms.computeIfAbsent(roomName, k -> new ArrayList<>()).add(client);
        client.setCurrentRoom(roomName);
        client.sendMessage("Joined room: " + roomName);
    }

    public void removeUserFromRoom(ConnectionHandler client, String roomName) {
        List<ConnectionHandler> room = chatRooms.get(roomName);
        if (room != null) {
            room.remove(client);
        }
        client.sendMessage("Left room: " + roomName);
    }

    public void broadcastToRoom(String roomName, String message) {
        List<ConnectionHandler> room = chatRooms.get(roomName);
        if (room != null) {
            for (ConnectionHandler client : room) {
                client.sendMessage("Sent in " + roomName + ": " + message);
            }
        }
    }

    public List<String> getRoomList() {
        return new ArrayList<>(chatRooms.keySet());
    }

}
