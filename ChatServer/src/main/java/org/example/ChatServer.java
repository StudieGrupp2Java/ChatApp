package org.example;

import org.example.commands.CommandManager;
import org.example.filter.ChatFilter;
import org.example.handling.ClientManager;
import org.example.users.UserManager;
import org.example.util.FileInformationHandler;

public class ChatServer {
    private FileInformationHandler fileInfo;
    private ChatFilter filter;
    private ClientManager clientManager;
    private UserManager userManager;
    private CommandManager commandManager;

    public ChatServer() {
        System.out.println("Starting ChatServer...");
        init();

        clientManager.listen();
    }

    private void init() {
        this.fileInfo = new FileInformationHandler();
        this.filter = new ChatFilter(fileInfo);
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

    public ChatFilter getChatFilter() {
        return filter;
    }
}
