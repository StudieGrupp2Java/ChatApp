package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class ResetColorCommand extends Command{
    public ResetColorCommand(ChatClient main) {
        super("/resetcolor", "resetcolor <out/in> resets the text color", main);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: " + this.getDesc());
            return;
        }
        main.getTextColor().resetTextColor(args[1]);
    }
}
