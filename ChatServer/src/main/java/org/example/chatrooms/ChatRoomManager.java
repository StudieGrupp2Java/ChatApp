package org.example.chatrooms;

import lombok.Getter;
import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;
import org.example.util.ChatLog;
import org.example.util.Util;

import java.util.*;

public class ChatRoomManager {
    @Getter
    private final Map<String, List<ConnectionHandler>> chatRooms = new HashMap<>();
    @Getter
    private final Map<String, List<ChatLog>> chatRoomLogs = new HashMap<>();
    private final ChatServer main;
    public ChatRoomManager(ChatServer main){
        this.main = main;

        chatRooms.putIfAbsent("Default", new ArrayList<>());
        chatRoomLogs.putIfAbsent("Default", new ArrayList<>());
    }

    public void loadRoom(String roomName) {
        chatRooms.put(roomName, new ArrayList<>());
        chatRoomLogs.put(roomName, new ArrayList<>());
    }

    public void createRoom(String roomName, ConnectionHandler sender) {
        this.loadRoom(roomName);
        sender.sendMessage("Created room: " + roomName);
    }

    // Returns true if the room exists
    public boolean addUserToRoom(ConnectionHandler client, String roomName) {
        if (!chatRooms.containsKey(roomName)){
            client.sendMessage("No room with that name exists!");
            return false;
        }
        if (!chatRooms.get(roomName).contains(client)) {
            chatRooms.get(roomName).add(client);
        }

        final User user = main.getUserManager().getUser(client.getIdentifier());
        user.setCurrentRoom(roomName);
        client.sendMessage("Joined room: " + roomName);

        List<ChatLog> logs = chatRoomLogs.get(roomName);

        if (logs != null && !logs.isEmpty()) {
            logs.stream()
                    .sorted(Comparator.comparingLong(ChatLog::getTimestamp).reversed())
                    .limit(30)
                    .sorted(Comparator.comparingLong(ChatLog::getTimestamp))
                    .map(log -> String.format("[%s][%s] %s", roomName, Util.DATE_FORMAT.format(log.getTimestamp()), log.getMessage()))
                    .forEach(client::sendMessage);
        }

        main.getClientManager().broadcastMessageInRoom(Util.formatUserName(user) + " joined the chat!", true, user);
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

    public List<String> getRoomList() {
        return new ArrayList<>(chatRooms.keySet());
    }

    public void addToChatLog(String roomName, String message){
        // Create new chatlog object for the room if it doesnt exist
        chatRoomLogs.computeIfAbsent(roomName, k -> new ArrayList<>());

        // add the message to the chatlog
        chatRoomLogs.get(roomName).add(new ChatLog(System.currentTimeMillis(), message));
    }

    public boolean roomExists(String room) {
        return this.chatRooms.containsKey(room);
    }

    public List<ConnectionHandler> getUsersIn(String room) {
        return this.chatRooms.get(room);
    }

}
