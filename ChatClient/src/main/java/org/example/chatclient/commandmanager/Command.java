package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public abstract class Command {
    private final String name;
    private final String desc;
    protected final ChatClient main;

    public Command(String name, String desc, ChatClient main){
        this.name = name;
        this.desc = desc;
        this.main = main;
    }

    public abstract void execute(String[] args);

    public String getName(){
        return name;
    }

    protected String getDesc() {
        return this.desc;
    }
}