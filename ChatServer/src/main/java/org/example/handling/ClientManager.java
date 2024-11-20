package org.example.handling;

import org.example.ChatServer;
import org.example.users.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.HashMap;

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
                    User user = main.getUserManager().getUser(handler.getIdentifier());
                    if (user == null)
                        handler.getWriter().println("Please register with /register <username> <password> or login with /login <username> <password>");
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
        main.getUserManager().removeUser(connection.getIdentifier());
        System.out.println(connection.getSocket() + " disconnected!");
    }

    public Collection<ConnectionHandler> getConnections() {
        return connections.values();
    }
}
