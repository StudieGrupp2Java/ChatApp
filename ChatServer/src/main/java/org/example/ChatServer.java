package org.example;

import org.example.commands.CommandManager;
import org.example.users.UserManager;

public class ChatServer {
    private ClientManager clientManager;
    private UserManager userManager;
    private CommandManager commandManager;

    public ChatServer() {
        System.out.println("Starting ChatServer...");
        init();

        clientManager.listen();
    }

    private void init() {
        this.commandManager = new CommandManager(this);
        this.clientManager = new ClientManager(this);
        this.userManager = new UserManager(this);
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
