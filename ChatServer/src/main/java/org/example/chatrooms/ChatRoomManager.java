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
    @Getter
    private final Map<String, List<ConnectionHandler>> dmMap = new HashMap<>();
    @Getter
    private final Map<String, List<ChatLog>> dmLogs = new HashMap<>();
    private final ChatServer main;
    private int nmr = 0;


    public ChatRoomManager(ChatServer main) {
        this.main = main;

        chatRooms.putIfAbsent("Default", new ArrayList<>());
        chatRoomLogs.putIfAbsent("Default", new ArrayList<>());
    }

    public void loadRoom(String roomName) {
        chatRooms.put(roomName, new ArrayList<>());
        chatRoomLogs.put(roomName, new ArrayList<>());
    }

    private void loadDMRoom(String roomName) {
        dmMap.putIfAbsent(roomName, new ArrayList<>());
        dmLogs.putIfAbsent(roomName, new ArrayList<>());

    }

    public void createDMRoom(ConnectionHandler recipient, ConnectionHandler sender, String roomName) {
        for (String room : dmMap.keySet()){
            if (dmMap.get(room).contains(sender) && dmMap.get(room).contains(recipient)) return;
        }
        loadDMRoom(roomName);
        dmMap.get(roomName).add(sender);
        dmMap.get(roomName).add(recipient);
        sender.sendMessage("Created DM-Room");
    }

    public int incrementDMNmr(){
        return nmr++;
    }

    public void createRoom(String roomName, ConnectionHandler sender) {
        this.loadRoom(roomName);
        sender.sendMessage("Created room: " + roomName);
    }

    public void addUserToDMRoom(ConnectionHandler client, ConnectionHandler recipient) {
        final User user = main.getUserManager().getUser(client.getIdentifier());
        final User recipientUser = main.getUserManager().getUser(recipient.getIdentifier());
        for (String room : dmMap.keySet()) {
            if (dmMap.get(room).contains(recipient) && dmMap.get(room).contains(client)) {
                client.sendMessage("Joined DM chat");
                user.setCurrentRoom(room);
                user.setInDMS(true);
                if (recipientUser.getCurrentRoom().equals(user.getCurrentRoom())){
                    recipient.sendMessage(recipientUser.getName() + " joined the DM Chat! You are now here together");
                }
                List<ChatLog> logs = dmLogs.get(room);
                if (logs != null && !logs.isEmpty()) {
                    logs.stream()
                            .sorted(Comparator.comparingLong(ChatLog::getTimestamp).reversed())
                            .limit(30)
                            .sorted(Comparator.comparingLong(ChatLog::getTimestamp))
                            .map(log -> String.format("[%s][%s] %s", "DM", Util.DATE_FORMAT.format(log.getTimestamp()), log.getMessage()))
                            .forEach(client::sendMessage);
                }
            }
        }
    }

    // Returns true if the room exists
    public boolean addUserToRoom(ConnectionHandler client, String roomName) {
        if (!chatRooms.containsKey(roomName)) {
            client.sendMessage("No room with that name exists!");
            return false;
        }
        if (!chatRooms.get(roomName).contains(client)) {
            chatRooms.get(roomName).add(client);
        }

        final User user = main.getUserManager().getUser(client.getIdentifier());
        user.setCurrentRoom(roomName);
        user.setInDMS(false);
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

    public void addToChatLog(String roomName, String message) {
        // Create new chatlog object for the room if it doesnt exist
        chatRoomLogs.computeIfAbsent(roomName, k -> new ArrayList<>());

        // add the message to the chatlog
        chatRoomLogs.get(roomName).add(new ChatLog(System.currentTimeMillis(), message));
    }

    public void addDMChatLogs(String roomName, String message){
        dmLogs.computeIfAbsent(roomName, k -> new ArrayList<>());

        dmLogs.get(roomName).add(new ChatLog(System.currentTimeMillis(), message));
    }

    public boolean roomExists(String room) {
        return this.chatRooms.containsKey(room);
    }

    public List<ConnectionHandler> getUsersIn(String room) {
        return this.chatRooms.get(room);
    }

}
