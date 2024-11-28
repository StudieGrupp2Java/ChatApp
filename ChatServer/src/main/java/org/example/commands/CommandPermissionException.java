package org.example.commands;

public class CommandPermissionException extends Exception {
    public CommandPermissionException(String reason) {
        super(reason);
    }
}
