package org.example.users;

import lombok.Getter;
import lombok.Setter;
import org.example.handling.ConnectionHandler;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private final int identifier;
    private final String name;
    private String password; //TODO: store encrypted or other more secure way
    @Getter
    @Setter
    private ChatRole role;
    private boolean pendingDeletion = false;

    @Getter
    private List<String> blockedUsers;
    @Getter
    @Setter
    private String currentRoom;

    private Status status;
    private long lastSeen;


    public User(String name, String password) {
        this.identifier = Math.abs(UUID.randomUUID().hashCode()); // ensure positive identifier
        this.name = name;
        this.password = password;
        this.blockedUsers = new ArrayList<>();
        this.currentRoom = "Default";
        this.role =  ChatRole.USER;
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
        if (status != Status.AWAY) {
            this.lastSeen = System.currentTimeMillis();
        }
    }

    public void addBlockedUser(String name, ConnectionHandler sender){
        if (blockedUsers.contains(name)){
            sender.sendMessage("User is already blocked!");
            return;
        }
        blockedUsers.add(name);
    }

    public void removeBlockedUser(String name, ConnectionHandler sender){
        if (!blockedUsers.contains(name)){
            sender.sendMessage("User is not in your blocked list!");
            return;
        }
        blockedUsers.remove(name);
    }

    public Status getStatus() {
        return status;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setInitalStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        ONLINE,
        AWAY,
        OFFLINE
    }

    public boolean isPendingDeletion() {
        return pendingDeletion;
    }

    public void setPendingDeletion(boolean pendingDeletion) {
        this.pendingDeletion = pendingDeletion;
    }
}
