package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;

public class ChangePasswordCommand extends Command {

    public ChangePasswordCommand(ChatServer main, ConnectionHandler sender) {
        super(main, sender);
    }

    @Override
    public void execute(String[] args) {

        String oldPassword = args[0];
        String newPassword = args[1];

        // TODO: Lägg till logik för att byta lösenord
        System.out.println("Changing password...");
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
