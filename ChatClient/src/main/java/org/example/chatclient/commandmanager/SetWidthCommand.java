package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class SetWidthCommand extends Command {
    public SetWidthCommand(ChatClient main) {
        super("/setwidth", main);
    }

    @Override
    public void execute(String[] args) {
        try {
            int width = Integer.parseInt(args[1]);
            main.getDrawer().setWidth(width);
            System.out.println("Set console width to " + width);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
    }
}
