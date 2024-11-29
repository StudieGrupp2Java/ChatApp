package org.example.handling;

import org.example.ChatServer;
import org.example.users.User;
import org.example.util.Util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.*;


public class ClientManager {
    private static final int SERVER_PORT = 2147; //TODO: changeable
    private final ChatServer main;

    private final HashMap<Integer, ConnectionHandler> connections = new HashMap<>();

    public ClientManager(ChatServer chatServer) {
        this.main = chatServer;
    }

    public void listen() {
        try (ServerSocket socket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started on port " + SERVER_PORT);
            System.out.println("Listening...");


            while (true) {
                try {
                    ConnectionHandler handler = new ConnectionHandler(main, socket.accept());
                    this.addConnection(handler);
                    handler.start();
                } catch (IOException e) {
                    System.err.println("Error handling new connection");
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            System.err.println("Error starting ChatServer");
            e.printStackTrace();
        }
    }

    public void addConnection(ConnectionHandler connection) {
        connections.put(connection.getIdentifier(), connection);
        System.out.println(connection.getSocket() + " connected!");
    }

    public void removeConnection(ConnectionHandler connection) {
        connection.close();
        connections.remove(connection.getIdentifier());
        System.out.println(connection.getSocket() + " disconnected!");

        final User user = main.getUserManager().getUser(connection.getIdentifier());
        if (user != null) {
            this.broadcastMessageInRoom(Util.formatUserName(user) + " disconnected!", true, user);
            user.setStatus(User.Status.OFFLINE);
        }
        main.getClientManager().logout(connection);
    }

    public boolean isConnected(int identifier) {
        return connections.get(identifier) != null;
    }

    public synchronized void broadcastMessageInRoom(String message, boolean onlyLoggedIn, User currentSender) {
        if (currentSender == null) {
            return;
        }
        if (currentSender.isInDMS()){
            ConnectionHandler sender = main.getClientManager().getConnections().get(currentSender.getIdentifier());
            broadcastDM(message, sender, currentSender.getRecipient());
            return;
        }
        if (!main.getChatRoomManager().roomExists(currentSender.getCurrentRoom())){
            return;
        }

        String timestamp = "[" + Util.DATE_FORMAT.format(System.currentTimeMillis()) + "]";
        final String toSend = timestamp + " " + message;

        List<ConnectionHandler> room = main.getChatRoomManager().getUsersIn(currentSender.getCurrentRoom());
        final String username = currentSender.getName();
        room.stream()
                .filter(connection -> !onlyLoggedIn || connection.isLoggedIn())
                .forEach(connection -> {
                    User user = main.getUserManager().getUser(connection.getIdentifier());
                    if (!user.getBlockedUsers().contains(username) && !user.isInDMS()){
                        connection.sendMessage("[" + user.getCurrentRoom() + "]" + toSend);
                    }
                });

        // Save to chat history
        main.getChatRoomManager().addToChatLog(currentSender.getCurrentRoom(), message);
    }

    public synchronized void broadcastDM(String message, ConnectionHandler sender, User recipient) {
        if (sender == null) return;
        User zender = main.getUserManager().getUser(sender.getIdentifier());

        String timestamp = "[" + Util.DATE_FORMAT.format(System.currentTimeMillis()) + "]";
        final String toSend = timestamp + " " + message;
        ConnectionHandler recipientConnection = this.getConnections().get(recipient.getIdentifier());

        for (String room : main.getChatRoomManager().getDmMap().keySet()){
            if (main.getChatRoomManager().getDmMap().get(room).contains(sender) && main.getChatRoomManager().getDmMap().get(room).contains(recipientConnection)){
                if (recipient.getCurrentRoom().equals(room)){
                    for (ConnectionHandler handler : main.getChatRoomManager().getDmMap().get(room)){
                        handler.sendMessage("[DM]" + toSend);
                    }
                    main.getNotificationManager().sendNotification(recipientConnection, "dm");
                    main.getChatRoomManager().addDMChatLogs(room, message);
                    return;
                } else {
                    sender.sendMessage("[DM]" + toSend);
                    recipientConnection.sendMessage(zender.getName() + " sent you a DM. Type /dm " + zender.getName() + " join, to see what they wrote!");
                    main.getNotificationManager().sendNotification(recipientConnection, "dm");
                    main.getChatRoomManager().addDMChatLogs(room, message);
                }
            }
        }

    }



    public Map<Integer, ConnectionHandler> getConnections(){
        return connections;
    }

    public void login(ConnectionHandler sender, User user) {
        connections.remove(sender.getIdentifier());
        sender.login(user);
        user.setStatus(User.Status.ONLINE);
        connections.put(sender.getIdentifier(), sender);
        if (user.getCurrentRoom().contains("DM-Room")) user.setCurrentRoom("Default");

        main.getChatRoomManager().addUserToRoom(sender, user.getCurrentRoom());
    }

    public void logout(ConnectionHandler sender) {
        connections.remove(sender.getIdentifier());
        sender.logout();
        connections.put(sender.getIdentifier(), sender);
    }

    public ConnectionHandler get(int identifier) {
        return connections.get(identifier);
    }
}
