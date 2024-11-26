package org.example.users;

import java.io.Serializable;

public class User implements Serializable {
    private int identifier;
    private final String name;
    private String password; //TODO: store encrypted or other more secure way
    private ChatRole role;
    private boolean pendingDeletion = false;

    public User(int identifier, String name, String password) {
        this.identifier = identifier;
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

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public boolean isPendingDeletion() {
        return pendingDeletion;
    }

    public void setPendingDeletion(boolean pendingDeletion) {
        this.pendingDeletion = pendingDeletion;
    }
}
