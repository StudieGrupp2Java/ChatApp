package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class SetBackgroundCommand extends Command{
    public SetBackgroundCommand(ChatClient main) {
        super("/setbg", "setbg <color> <out/in> - sets the background color", main);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: " + this.getDesc());
            return;
        }
        String check = args[2];
        String color = args[1];
        main.getTextColor().setBackgroundColor(color, check);
    }
}
