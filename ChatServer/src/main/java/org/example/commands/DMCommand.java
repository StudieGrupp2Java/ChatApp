package org.example.commands;

public class DMCommand extends Command {

    @Override
    public void execute(String[] args) {
        validateArgs(args, 2); // Förväntar sig mottagare och meddelande

        String recipient = args[0];
        String message = args[1];

        // TODO: Lägg till logik för att skicka ett DM
        System.out.println("Sending DM to " + recipient + ": " + message);
    }
}
