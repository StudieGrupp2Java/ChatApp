package org.example.commandmanager;

import org.example.chatclient.ChatClient;

public abstract class Command {
    private final String name;
    protected final ChatClient main;

    public Command(String name, ChatClient main){
        this.name = name;
        this.main = main;
    }

    public abstract void execute(String[] args);

    public String getName(){
        return name;
    }
}