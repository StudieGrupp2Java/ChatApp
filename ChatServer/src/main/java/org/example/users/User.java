package org.example.users;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private final int identifier;
    private final String name;
    private String password; //TODO: store encrypted or other more secure way
    private ChatRole role;

    private Status status = Status.OFFLINE;
    private long lastSeen = -1;

    public User(int identifier, String name, String password) {
        this.identifier = identifier;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password) {
        this.identifier = Math.abs(UUID.randomUUID().hashCode()); // ensure positive identifier
        this.name = name;
        this.password = password;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setOnline() {
        this.lastSeen = System.currentTimeMillis();
        this.status = Status.ONLINE;
    }

    public enum Status {
        ONLINE,
        AWAY,
        OFFLINE
    }
}
