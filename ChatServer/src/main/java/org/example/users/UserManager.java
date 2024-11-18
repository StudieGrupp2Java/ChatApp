package org.example.users;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class UserManager {
    private final HashMap<ConnectionHandler, User> users =  new HashMap<>();
    private int index = 0;

    public UserManager(ChatServer chatServer) {
    }

    public void addUser(ConnectionHandler connection) {
        users.put(connection, new User(++index, "Default", connection));
        System.out.println(connection.getSocket() + " connected!");
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public void removeUser(ConnectionHandler connection) throws IOException {
        connection.close();
        users.remove(connection);
        System.out.println(connection.getSocket() + " disconnected!");
    }
}
