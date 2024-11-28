package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class CommandManager {
    private final ChatServer main;

    public CommandManager(ChatServer chatServer) {
        this.main = chatServer;
    }

    public void handleIncomingCommand(String input, ConnectionHandler sender) {
        if (input.startsWith("/")) {
            // Detta är ett kommando, t.ex. /register "message"
            String[] parts = input.split(" ", 2);
            String commandName = parts[0].substring(1).toLowerCase(); // Tar bort "/" och konverterar till små bokstäver
            String[] args = parseArgs(parts.length > 1 ? parts[1] : "");

            try {
                Command command = CommandFactory.getCommand(commandName);
                command.executeWithValidation(args, main, sender);

            } catch (IllegalArgumentException | CommandPermissionException e) {
                sender.sendMessage(e.getMessage());
            }
        } else if (input.startsWith("@")) {
            // DM-kommandot, t.ex. @username message
            String[] parts = input.split(" ", 2);
            String username = parts[0].substring(1); // Tar bort "@"
            String message = parts.length > 1 ? parts[1] : "";

            // Hämta DMCommand från CommandFactory och exekvera med rätt argument
            try {
                Command dmCommand = CommandFactory.getCommand("dm");
                dmCommand.executeWithValidation(new String[]{username, message}, main, sender);
            } catch (IllegalArgumentException | CommandPermissionException e) {
                sender.sendMessage(e.getMessage());
            }
        } else {
            System.out.println("Ogiltigt kommandoformat");
        }
    }

    private String[] parseArgs(String argString) {
        // Enkel metod för att dela upp argument där citattecken används för flera ord
        if (argString.startsWith("\"") && argString.endsWith("\"")) {
            return new String[]{argString.substring(1, argString.length() - 1)};
        }
        return argString.split(" ");
    }
}