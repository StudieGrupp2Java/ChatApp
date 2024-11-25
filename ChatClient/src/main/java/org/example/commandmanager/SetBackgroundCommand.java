package org.example.commandmanager;

import org.example.chatclient.ChatClient;

public class SetBackgroundCommand extends Command{
    public SetBackgroundCommand(ChatClient main) {
        super("/setbg", main);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) return;
        String check = args[2];
        String color = args[1];
        main.getTextColor().setBackgroundColor(color, check);
    }
}
