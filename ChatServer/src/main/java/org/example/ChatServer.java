package org.example;

import org.example.commands.CommandManager;
import org.example.filemanager.FileManager;
import org.example.filter.ChatFilter;
import org.example.handling.ClientManager;
import org.example.users.UserManager;
import org.example.util.ChatLogs;
import org.example.util.UpdateTracker;

public class ChatServer {
    private FileManager fileManager;
    private ChatLogs chatInfo;
    private ChatFilter filter;
    private ClientManager clientManager;
    private UserManager userManager;
    private CommandManager commandManager;
    private UpdateTracker updateTracker;

    public ChatServer() {
        System.out.println("Starting ChatServer...");
        init();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        updateTracker.runTick();
        clientManager.listen();
    }

    private void init() {
        this.userManager = new UserManager(this);
        this.chatInfo = new ChatLogs();
        this.fileManager = new FileManager(this);
        this.filter = new ChatFilter(chatInfo);
        this.commandManager = new CommandManager(this);
        this.clientManager = new ClientManager(this);
        this.updateTracker = new UpdateTracker(this);
    }

    private void shutdown() {
        fileManager.saveAll();
        updateTracker.close();
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

    public FileManager getFileManager() {
        return fileManager;
    }

    public UpdateTracker getUpdateTracker() {
        return updateTracker;
    }
}
