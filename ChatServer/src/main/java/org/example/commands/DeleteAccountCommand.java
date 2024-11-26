package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;
import org.example.users.UserManager;
import org.example.util.ChatLog;

public class DeleteAccountCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {

        UserManager userManager = main.getUserManager();
        User user = userManager.getUser(sender.getIdentifier());
        
        // Kolla om användaren är inloggad, annars returnera felmeddelande
        if (user == null){
            sender.sendMessage("You're not logged in and can't delete your account.");
            return;
        }

        // Om användaren redan är markerad för radering, meddela att den måste bekräfta
        if (user.isPendingDeletion()){
            sender.sendMessage("Your account is already marked for deletion. Confirm with /confirmdelete ");
            return;
        }

        // Markera användaren för "väntar på radering"
        user.setPendingDeletion(true);
        sender.sendMessage("Your account is now marked for deletion. Confirm with /confirmdelete ");
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
