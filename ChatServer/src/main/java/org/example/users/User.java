package org.example.users;

public class User {
    private int identifier;
    private String name;
    private String password; //TODO: store encrypted or other more secure way

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
}
