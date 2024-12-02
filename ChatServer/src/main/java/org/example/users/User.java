package org.example.users;

import lombok.Getter;
import lombok.Setter;
import org.example.handling.ConnectionHandler;
import org.example.passwordencryption.Encryptor;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.KeyPair;
import java.util.*;

public class User implements Serializable {
    private final int identifier;
    private final String name;
    private String password; //TODO: store encrypted or other more secure way
    @Getter
    @Setter
    private ChatRole role;
    private boolean pendingDeletion = false;
    private UserNotificationSettings notificationSettings;

    @Getter
    private List<String> blockedUsers;
    @Getter
    @Setter
    private String currentRoom;
    @Getter
    @Setter
    private boolean inDMS;
    @Getter
    @Setter
    private User recipient;

    private final byte[] salt;

    private Status status;
    private long lastSeen;


    public User(String name, String password) {
        this.identifier = Math.abs(UUID.randomUUID().hashCode()); // ensure positive identifier
        this.name = name;
        this.salt = Encryptor.generateSalt();
        this.password = Encryptor.hashPassword(password, this.salt);
        this.blockedUsers = new ArrayList<>();
        this.currentRoom = "Default";
        this.inDMS = false;
        this.notificationSettings = new UserNotificationSettings();
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

    public UserNotificationSettings getNotificationSettings(){
        return notificationSettings;
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

    public boolean passwordMatches(String decryptedPassword) {
        System.out.println(password);
        return password.equals(Encryptor.hashPassword(decryptedPassword, this.salt));
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
