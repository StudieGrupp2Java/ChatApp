package org.example.commands;

import org.example.ChatServer;

public class CommandManager {
    public CommandManager(ChatServer chatServer) {
    }

    public void handleIncomingCommand(String input) {
        if (input.startsWith("/")) {
            // Detta är ett kommando, t.ex. /register "message"
            String[] parts = input.split(" ", 2);
            String commandName = parts[0].substring(1).toLowerCase(); // Tar bort "/" och konverterar till små bokstäver
            String[] args = parseArgs(parts.length > 1 ? parts[1] : "");

            Command command = CommandFactory.getCommand(commandName);
            if (command != null) {
                try {
                    command.execute(args);
                } catch (IllegalArgumentException e){
                    System.out.println("Felaktiga argument: " + e.getMessage());
                }
            }else {
                System.out.println("Okänt kommando: " + commandName);
            }
        } else if (input.startsWith("@")) {
            // DM-kommandot, t.ex. @username message
            String[] parts = input.split(" ",2);
            String username = parts[0].substring(1); // Tar bort "@"
            String message = parts.length > 1 ? parts[1] : "";

            // Hämta DMCommand från CommandFactory och exekvera med rätt argument
            Command dmCommand = CommandFactory.getCommand("dm");
            if (dmCommand != null) {
                try {
                    dmCommand.execute(new String[]{username, message});
                } catch (IllegalArgumentException e) {
                    System.out.println("Felaktiga argument: " + e.getMessage());
                }
            } else {
                System.out.println("Okänt DM-kommando");
            }
        }else {
            System.out.println("Ogiltigt kommandoformat");
        }
    }

    private String[] parseArgs(String argString) {
        // Enkel metod för att dela upp argument där citattecken används för flera ord
        if (argString.startsWith("\"") && argString.endsWith("\"")){
            return new String[]{argString.substring(1, argString.length() - 1)};
        }
        return argString.split(" ");
    }
}