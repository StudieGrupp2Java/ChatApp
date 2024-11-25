package org.example.handling;

import org.example.ChatServer;
import org.example.users.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ClientManager {
    private static final int SERVER_PORT = 2147; //TODO: changeable
    private final ChatServer main;
    private final int MESSAGESTOSHOW = 30;
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
            this.broadcastMessage(user.getName() + " disconnected!", true);
        }
    }

    public boolean isConnected(int identifier) {
        return connections.get(identifier) != null;
    }

    /**
     * Broadcasts message to all connected users
     * @param message to be broadcasted
     * @param onlyLoggedIn whether to only send to logged-in users
     */
    public void broadcastMessage(String message, boolean onlyLoggedIn) {
        final String username = getUsername(message);
        connections.values().stream()
                .filter(connection -> !onlyLoggedIn || connection.isLoggedIn())
                .forEach(connection -> {
                    User user = main.getUserManager().getUser(connection.getIdentifier());
                    if (!user.getBlockedUsers().contains(username)){
                        connection.sendMessage(message);
                    }
                });
    }

    private String getUsername(String message){
        String[] split = message.split("] ");
        String test = "";
        if (split.length >= 2)
            test = split[1].split(": ")[0];
        return test;
    }

    public void login(ConnectionHandler sender, User user) {
        connections.remove(sender.getIdentifier());
        sender.login(user);
        user.setStatus(User.Status.ONLINE);
        connections.put(sender.getIdentifier(), sender);
        main.getChatInfo().getChatLogs().stream().sorted(Comparator.reverseOrder()).limit(MESSAGESTOSHOW).forEach(sender::sendMessage);
    }

    public void logout(ConnectionHandler sender) {
        connections.remove(sender.getIdentifier());
        sender.logout();
        connections.put(sender.getIdentifier(), sender);
    }
}
