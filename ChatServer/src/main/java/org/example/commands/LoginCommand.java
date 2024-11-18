package org.example.commands;

public class LoginCommand extends Command {

    @Override
    public void execute(String[] args) {
        validateArgs(args, 2); // Förväntar sig username och password

        String username = args[0];
        String password = args[1];

        // TODO: Lägg till logik för att autentisera användaren
        System.out.println("Logging in user: " + username);
    }
}
