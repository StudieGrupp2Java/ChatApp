package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    // Static initialization block
    public static void initialize(ChatServer main, ConnectionHandler sender) {
        commandMap.put("login", () -> new LoginCommand(main, sender));
        commandMap.put("register", () -> new RegisterCommand(main, sender));
        commandMap.put("dm", () -> new DMCommand(main, sender));
        commandMap.put("changepassword", () -> new ChangePasswordCommand(main, sender));
    }

    // Static method to get command
    public static Command getCommand(String commandName) {
        Supplier<Command> commandSupplier = commandMap.get(commandName.toLowerCase());
        if (commandSupplier != null) {
            return commandSupplier.get();
        }
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }
}
