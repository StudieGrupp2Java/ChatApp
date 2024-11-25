package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

public class LogoutCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        User user = main.getUserManager().getUser(sender.getIdentifier());

        if (user == null) {
            sender.sendMessage("You're not logged in.");
            return;
        }

        sender.sendMessage("Logging out...");
        main.getClientManager().logout(sender);
        sender.sendMessage("Bye!");

        user.setStatus(User.Status.OFFLINE);
        main.getClientManager().broadcastMessage(user.getName() + " logged out!", true);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
