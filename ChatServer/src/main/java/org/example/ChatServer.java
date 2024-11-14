package org.example;

import org.example.commands.CommandManager;
import org.example.users.UserManager;

public class ChatServer {
    private ServerManager serverManager;
    private UserManager userManager;
    private CommandManager commandManager;

    public ChatServer() {
        System.out.println("Starting ChatServer...");
        init();

//        serverManager.listen();
    }

    private void init() {
        this.commandManager = new CommandManager();
        this.serverManager = new ServerManager();
        this.userManager = new UserManager();
    }
}
