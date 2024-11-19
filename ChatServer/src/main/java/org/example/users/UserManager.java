package org.example.users;

import org.example.ChatServer;

import java.util.Collection;
import java.util.HashMap;

public class UserManager {
    private final HashMap<Integer, User> users =  new HashMap<>();

    public UserManager(ChatServer chatServer) {
    }

    // Add user here when they authenticate via login/register command or prompt on login
    public void addUser(int identifier, String name) {
        users.put(identifier, new User(identifier, name));
        System.out.println(name + " connected!");
    }

    public void removeUser(int identifier) {
        final User user = this.getUser(identifier);
        users.remove(identifier);
        System.out.println(user.getName() + " disconnected!");
    }

    public User getUser(int identifier) {
        return users.get(identifier);
    }

    public Collection<User> getUsers() {
        return users.values();
    }
}
