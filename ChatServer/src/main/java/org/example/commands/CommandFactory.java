package org.example.commands;

import org.example.ChatServer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    public CommandFactory(ChatServer main) {
        commandMap.put("login", () -> new LoginCommand(main));
        commandMap.put("register", () -> new RegisterCommand(main));
        commandMap.put("dm", () -> new DMCommand(main));
        commandMap.put("changepassword", () -> new ChangePasswordCommand(main));
    }

    public Command getCommand(String commandName) {
        Supplier<Command> commandSupplier = commandMap.get(commandName.toLowerCase());
        if (commandSupplier != null) {
            return commandSupplier.get();
        }
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }
}
