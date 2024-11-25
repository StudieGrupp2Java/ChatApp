package org.example.chatrooms;

import lombok.Getter;
import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

import java.util.*;

public class ChatRoom {
    @Getter
    private final Map<String, List<ConnectionHandler>> chatRooms = new HashMap<>();
    @Getter
    private final Map<String, List<String>> chatRoomLogs = new HashMap<>();
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
        main.getUserManager().getUser(client.getIdentifier()).setCurrentRoom(roomName);
        client.sendMessage("Joined room: " + roomName);
        User user = main.getUserManager().getUser(client.getIdentifier());
        main.getClientManager().broadcastMessage(user.getName() + " joined the chat!", true);
    }

    public void removeUserFromRoom(ConnectionHandler client, String roomName) {
        List<ConnectionHandler> room = chatRooms.get(roomName);
        if (room != null) {
            room.remove(client);
        }
        client.sendMessage("Left room: " + roomName);
        User user = main.getUserManager().getUser(client.getIdentifier());
        main.getClientManager().broadcastMessage(user.getName() + " left the chat!", true);
    }

    public List<String> getRoomList() {
        return new ArrayList<>(chatRooms.keySet());
    }

    public void addToChatLog(String roomName, String message){
        if (chatRoomLogs.containsKey(roomName)){
            chatRoomLogs.get(roomName).add(message);
        }
        System.out.println("Added logs to " + roomName + " with " + message);
    }

}
