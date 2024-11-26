package org.example.users;

import org.example.ChatServer;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class UserManager implements Serializable {
    private final ChatServer main;
    private final HashMap<Integer, User> users = new HashMap<>();

    public UserManager(ChatServer chatServer) {
        this.main = chatServer;
    }

    public void loadUser(int identifier, User user) {
        users.put(identifier, user);
    }

    // Add user here when they authenticate via login/register command or prompt on login
    public void addUser(int identifier, User user) {
        users.put(identifier, user);
        main.getFileManager().saveAll();
    }

    public void removeUser(int identifier) {
        users.remove(identifier);
    }

    public User getUser(int identifier) {
        return users.get(identifier);
    }

    public Optional<User> getUser(String username) {
        return users.values().stream()
                .filter(user -> user.getName().equalsIgnoreCase(username))
                .findAny();
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public boolean userExists(String username) {
        return users.values().stream().anyMatch(user -> user.getName().equalsIgnoreCase(username));
    }

}
