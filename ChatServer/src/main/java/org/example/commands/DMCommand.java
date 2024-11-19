package org.example.commands;

import org.example.ChatServer;

public class DMCommand extends Command {

    public DMCommand(ChatServer main) {
        super(main);
    }

    @Override
    public void execute(String[] args) {

        String recipient = args[0];
        String message = args[1];

        // TODO: Lägg till logik för att skicka ett DM
        System.out.println("Sending DM to " + recipient + ": " + message);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
