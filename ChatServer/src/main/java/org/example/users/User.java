package org.example.users;

import org.example.ConnectionHandler;

public class User {
    private int id;
    private String name;
    private ConnectionHandler handler;


    public User(int id, String name, ConnectionHandler handler) {
        this.id = id;
        this.name = name;
        this.handler = handler;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ConnectionHandler getHandler() {
        return handler;
    }
}
