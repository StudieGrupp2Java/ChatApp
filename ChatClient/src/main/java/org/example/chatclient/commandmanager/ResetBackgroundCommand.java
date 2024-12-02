package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class ResetBackgroundCommand extends Command{
    public ResetBackgroundCommand(ChatClient main) {
        super("/resetbg", "/resetbg <out/in> - reset the background color", main);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: " + this.getDesc());
            return;
        }
        main.getTextColor().resetBackgroundColor(args[1]);
    }
}
