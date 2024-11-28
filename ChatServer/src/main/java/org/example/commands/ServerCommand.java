package org.example.commands;

import org.example.ChatServer;

public abstract class ServerCommand {

    protected void executeWithValidation(String[] args, ChatServer main) {
        validateArgs(args, getExpectedArgsCount());
        execute(args, main);
    }

    protected abstract void execute(String[] args, ChatServer main);

    protected abstract int getExpectedArgsCount();

    protected void validateArgs(String[] args, int expectedArgs) {
        if (args.length < expectedArgs) {
            throw new IllegalArgumentException("Invalid arguments. Expected " + expectedArgs + " arguments.");
        }
    }
}
