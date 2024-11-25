package org.example.commandmanager;

import org.example.chatclient.ChatClient;

public class ResetColorCommand extends Command{
    public ResetColorCommand(ChatClient main) {
        super("/resetcolor", main);
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 2) main.getTextColor().resetTextColor(args[1]);
    }
}
