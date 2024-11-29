package org.example;

import org.example.chatrooms.ChatRoomManager;
import org.example.commands.CommandManager;
import org.example.filemanager.FileManager;
import org.example.filter.ChatFilter;
import org.example.handling.ClientManager;
import org.example.users.UserManager;
import org.example.util.NotificationManager;
import org.example.util.UpdateTracker;

public class ChatServer {
    private ChatRoomManager chatRoomManager;
    private FileManager fileManager;
    private ChatFilter filter;
    private ClientManager clientManager;
    private UserManager userManager;
    private CommandManager commandManager;
    private UpdateTracker updateTracker;
    private NotificationManager notificationManager;

    private ServerInputHandler inputHandler;

    public boolean running = true;

    public ChatServer() {
        System.out.println("Starting ChatServer...");
        init();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        updateTracker.runTick();
        inputHandler.listenForInput();
        clientManager.listen();
    }

    private void init() {
        this.userManager = new UserManager(this);
        this.filter = new ChatFilter();
        this.commandManager = new CommandManager(this);
        this.clientManager = new ClientManager(this);
        this.updateTracker = new UpdateTracker(this);
        this.chatRoomManager = new ChatRoomManager(this);
        this.inputHandler = new ServerInputHandler(this);
        this.fileManager = new FileManager(this);
        this.notificationManager = new NotificationManager();
    }

    private void shutdown() {
        System.out.println("Shutting down and saving data...");
        fileManager.saveAll();
        this.running = false;
        System.out.println("Save complete");
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

    public ChatRoomManager getChatRoomManager(){
        return chatRoomManager;
    }

    public ChatFilter getChatFilter() {
        return filter;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public UpdateTracker getUpdateTracker() {
        return updateTracker;
    }

    public NotificationManager getNotificationManager(){
        return notificationManager;
    }
}
