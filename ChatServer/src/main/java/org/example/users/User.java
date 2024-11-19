package org.example.users;

public class User {
    private int identifier;
    private String name;

    public User(int identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }
}
