package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public abstract class Command {

    // Template method to handle common functionality
    public final void executeWithValidation(String[] args, ChatServer main, ConnectionHandler sender) {
        validateArgs(args, getExpectedArgsCount());
        execute(args, main, sender);
    }

    protected abstract void execute(String[] args, ChatServer main, ConnectionHandler sender);
    protected abstract int getExpectedArgsCount();

    protected void validateArgs(String[] args, int expectedArgs) {
        if (args.length < expectedArgs) {
            throw new IllegalArgumentException("Invalid arguments. Expected " + expectedArgs + " arguments.");
        }
    }
}
