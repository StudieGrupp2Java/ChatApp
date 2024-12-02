package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class SetColorCommand extends Command{
    public SetColorCommand(ChatClient main) {
        super("/setcolor", "/setcolor <color> <out/in> - sets the text color", main);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: " + this.getDesc());
            return;
        }
        String check = args[2];
        String color = args[1];
        main.getTextColor().setTextColor(color, check);
    }
}
