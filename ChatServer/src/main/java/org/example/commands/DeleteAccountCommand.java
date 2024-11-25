package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;
import org.example.users.UserManager;

public class DeleteMeCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        // 1. Kolla om användaren är inloggad
        UserManager userManager = main.getUserManager();
        User user = userManager.getUser(sender.getIdentifier());

        if (user == null){
            sender.sendMessage("You're not logged in and can't delete your account.");
            return;
        }
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
    /**
     * Som en användare vill jag kunna radera mitt konto så att jag inte behöver ha kvar mina uppgifter
     *
     * Kunna radera sin användare om man vill
     *
     * /DeleteMe
     */
}
