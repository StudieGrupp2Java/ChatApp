package org.example.users;

import org.example.ChatServer;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class UserManager implements Serializable {
    private final HashMap<Integer, User> users =  new HashMap<>();

    public UserManager(ChatServer chatServer) {
    }

    // Add user here when they authenticate via login/register command or prompt on login
    public void addUser(int identifier, User user) {
        users.put(identifier, user);
        System.out.println(user.getName() + " connected!");
    }

    public void removeUser(int identifier) {
        final User user = this.getUser(identifier);
        users.remove(identifier);
        if (user != null) {
            System.out.println(user.getName() + " disconnected!");
        }
    }

    public User getUser(int identifier) {
        return users.get(identifier);
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public boolean userExists(String username) {
        return users.values().stream().anyMatch(user -> user.getName().toLowerCase().equals(username));
    }
}
