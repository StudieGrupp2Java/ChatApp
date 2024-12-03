package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class SetWidthCommand extends Command {
    public SetWidthCommand(ChatClient main) {
        super("/setwidth", "/setwidth <number> - sets the width of the group chat interface", main);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: " + this.getDesc());
            return;
        }
        try {
            int width = Integer.parseInt(args[1]);
            main.getDrawer().setWidth(width);
            System.out.println("Set console width to " + width);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
    }
}
