package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class HelpCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        sender.sendMessage("--Server-side commands--");
        for (String command : CommandFactory.getCommandMap().keySet()){
            sender.sendMessage("/" + command);
        }
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
