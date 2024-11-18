package org.example.users;

import org.example.ChatServer;
import org.example.ConnectionHandler;

import java.util.HashSet;
import java.util.Set;

public class UserManager {
    private final Set<User> users =  new HashSet<>();
    private int index = 0;

    public UserManager(ChatServer chatServer) {
    }

    public void addUser(ConnectionHandler connection) {
        users.add(new User(++index, "Default", connection));
        System.out.println(connection.getSocket() + " connected!");
    }

    public Set<User> getUsers() {
        return users;
    }
}
