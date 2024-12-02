package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;

public class RemoveBannedWordCommand extends Command {
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        String word = args[0];
        main.getChatFilter().removeBannedWord(word);
        sender.sendMessage("Removed " + word + " to banned word list.");
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.ADMIN;
    }
}
