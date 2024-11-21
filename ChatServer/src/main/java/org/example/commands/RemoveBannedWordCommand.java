package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class RemoveBannedWordCommand extends Command{
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        String word = args[0];
        main.getChatInfo().removeBannedWord(word);
        sender.sendMessage("Added " + word + " to banned word list.");
    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
