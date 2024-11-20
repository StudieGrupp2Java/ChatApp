package org.example.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    static {
        commandMap.put("login", LoginCommand::new);
        commandMap.put("register", RegisterCommand::new);
        commandMap.put("dm", DMCommand::new);
        commandMap.put("changepassword", ChangePasswordCommand::new);
        commandMap.put("logout", LogoutCommand::new);
    }

    public static Command getCommand(String commandName) {
        Supplier<Command> commandSupplier = commandMap.get(commandName.toLowerCase());
        if (commandSupplier != null) {
            return commandSupplier.get();
        }
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }
}