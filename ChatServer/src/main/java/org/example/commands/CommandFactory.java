package org.example.commands;

public class CommandFactory {

    public static Command getCommand(String commandName) {
        switch (commandName.toLowerCase()) {
            case "login":
                return new LoginCommand();
            case "register":
                return new RegisterCommand();
            case "dm":
                return new DMCommand();
            case "changepassword":
                return new ChangePasswordCommand();
            default:
                throw new IllegalArgumentException("Unknown command: " + commandName);
        }
    }
}
