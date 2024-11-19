package org.example.commands;

import org.example.ChatServer;

public class RegisterCommand extends Command {

    public RegisterCommand(ChatServer main) {
        super(main);
    }

    @Override
    public void execute(String[] args) {
        String username = args[0];
        String password = args[1];

        // TODO: Lägg till logik för att registrera användaren
        System.out.println("Registering user: " + username);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
