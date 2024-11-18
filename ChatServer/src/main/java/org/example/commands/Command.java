package org.example.commands;

public abstract class Command {

    // Abstrakt metod som alla kommandon måste implementera
    public abstract void execute(String[] args);

    // Valfritt: Validera argument om det behövs
    public void validateArgs(String[] args, int expectedArgs) {
        if (args.length < expectedArgs) {
            throw new IllegalArgumentException("Invalid arguments. Expected " + expectedArgs + " arguments.");
        }
    }
}
