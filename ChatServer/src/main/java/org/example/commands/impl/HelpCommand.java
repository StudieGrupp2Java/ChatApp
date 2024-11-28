package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.commands.CommandFactory;
import org.example.commands.CommandPermissionException;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;

import java.util.Map;

public class HelpCommand extends Command {
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        StringBuilder builder = new StringBuilder("Server-side commands: ");
        var commands = CommandFactory.getCommandMap();

        commands.entrySet().stream()
                .filter(entry -> {
                    Command command = entry.getValue().get();
                    try {
                        this.hasPermission(main, sender, command.getPermissionLevel());
                    } catch (CommandPermissionException e) {
                        return false;
                    }
                    return true;
                })
                .map(Map.Entry::getKey)
                .forEach(name -> {
                    builder.append("/");
                    builder.append(name);
                    builder.append(", ");
                });
        sender.sendMessage(builder.toString());
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.NONE;
    }
}
