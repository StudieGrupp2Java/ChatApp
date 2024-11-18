package org.example.commands;

public class RegisterCommand extends Command {

    @Override
    public void execute(String[] args) {
        validateArgs(args, 2); // Förväntar sig username och password

        String username = args[0];
        String password = args[1];

        // TODO: Lägg till logik för att registrera användaren
        System.out.println("Registering user: " + username);
    }
}
