package org.example.commands;

public abstract class Command {
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
