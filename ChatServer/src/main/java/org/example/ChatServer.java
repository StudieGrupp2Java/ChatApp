package org.example;

import org.example.commands.CommandFactory;
import org.example.commands.CommandManager;
import org.example.filemanager.FileManager;
import org.example.filter.ChatFilter;
import org.example.handling.ClientManager;
import org.example.users.UserManager;
import org.example.util.ChatLogs;

public class ChatServer {
    private FileManager fileManager;
    private ChatLogs chatInfo;
    private ChatFilter filter;
    private ClientManager clientManager;
    private UserManager userManager;
    private CommandManager commandManager;

    public ChatServer() {
        System.out.println("Starting ChatServer...");
        init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> fileManager.saveAll()));
        clientManager.listen();
    }

    private void init() {
        this.userManager = new UserManager(this);
        this.chatInfo = new ChatLogs();
        this.fileManager = new FileManager(this);
        this.filter = new ChatFilter(chatInfo);
        this.commandManager = new CommandManager(this);
        this.clientManager = new ClientManager(this);

        // 'null' används för sender just nu eftersom det behövs dynamiskt under exekvering
        //CommandFactory.initialize(this,null);

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
    public ChatLogs getChatInfo(){
        return chatInfo;
    }
}
