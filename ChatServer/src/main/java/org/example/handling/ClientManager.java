package org.example.handling;

import org.example.ChatServer;
import org.example.users.User;
import org.example.util.ChatLog;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;

public class ClientManager {
    private static final int SERVER_PORT = 2147; //TODO: changeable
    private final ChatServer main;
    private final int MESSAGESTOSHOW = 30;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

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
            user.setStatus(User.Status.OFFLINE);
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
        String timestamp = "[" + DATE_FORMAT.format(System.currentTimeMillis()) + "]";
        final String toSend = timestamp + " " + message;

        connections.values().stream()
                .filter(connection -> !onlyLoggedIn || connection.isLoggedIn())
                .forEach(connection -> connection.sendMessage(toSend));

        // Save to chat history
        main.getChatInfo().addMessage(message);
    }

    public void login(ConnectionHandler sender, User user) {
        connections.remove(sender.getIdentifier());
        sender.login(user);
        user.setStatus(User.Status.ONLINE);
        connections.put(sender.getIdentifier(), sender);

        main.getChatInfo().getChatLogs().stream()
                .sorted(Comparator.comparingLong(ChatLog::getTimestamp).reversed())
                .limit(MESSAGESTOSHOW)
                .sorted(Comparator.comparingLong(ChatLog::getTimestamp))
                .map(log -> String.format("[%s] %s", DATE_FORMAT.format(log.getTimestamp()), log.getMessage()))
                .forEach(sender::sendMessage);
    }

    public void logout(ConnectionHandler sender) {
        connections.remove(sender.getIdentifier());
        sender.logout();
        connections.put(sender.getIdentifier(), sender);
    }
}
