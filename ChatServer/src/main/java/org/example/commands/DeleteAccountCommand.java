package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;
import org.example.users.UserManager;
import org.example.util.ChatLogs;

public class DeleteMeCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {

        UserManager userManager = main.getUserManager();
        User user = userManager.getUser(sender.getIdentifier());
        //ChatLogs chatLogs = new ChatLogs();

        // 1. Kolla om användaren är inloggad
        if (user == null){
            sender.sendMessage("You're not logged in and can't delete your account.");
            return;
        }

        // 2. Radera användaren
        userManager.deleteUser(user.getName());

        // 3. Skicka bekräftelsemeddelande till klienten
        sender.sendMessage("Your account is now deleted. We're sorry to see you go!");

        // 4. Eventuellt: Logga händelse för administratörsändamål
        // ChatLogs.getChatLogs().logAction("User " + user.getName() + " deleted their account.");
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
