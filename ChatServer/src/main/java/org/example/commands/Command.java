package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public abstract class Command {
    protected final ChatServer main;
    protected final ConnectionHandler sender;

    public Command(ChatServer main, ConnectionHandler connection) {
        this.sender = connection;
        this.main = main;
    }
    // Template method to handle common functionality
    public final void executeWithValidation(String[] args) {
        validateArgs(args, getExpectedArgsCount());
        execute(args);
    }

    protected abstract void execute(String[] args);
    protected abstract int getExpectedArgsCount();

    protected void validateArgs(String[] args, int expectedArgs) {
        if (args.length < expectedArgs) {
            throw new IllegalArgumentException("Invalid arguments. Expected " + expectedArgs + " arguments.");
        }
    }
}
