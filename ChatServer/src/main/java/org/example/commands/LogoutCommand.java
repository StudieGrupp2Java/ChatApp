package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

public class LogoutCommand extends Command {

    public LogoutCommand(ChatServer main, ConnectionHandler connection) {
        super(main, connection);
    }

    @Override
    protected void execute(String[] args) {
        User user = main.getUserManager().getUser(sender.getIdentifier());

        if (user == null) {
            sender.sendMessage("You're not logged in.");
            return;
        }

        sender.sendMessage("Logging out...");
        main.getUserManager().logout(sender.getIdentifier());
        sender.sendMessage("Bye!");

        main.getClientManager().broadcastMessage(user.getName() + " logged out!", true);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }
}
