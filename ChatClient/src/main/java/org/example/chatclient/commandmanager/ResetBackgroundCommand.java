package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class ResetBackgroundCommand extends Command{
    public ResetBackgroundCommand(ChatClient main) {
        super("/resetbg", main);
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 2) main.getTextColor().resetBackgroundColor(args[1]);
    }
}
