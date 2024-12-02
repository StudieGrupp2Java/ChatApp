package org.example.chatclient;


import org.example.chatclient.commandmanager.CommandManager;
import org.example.chatclient.filemanager.FileManager;
import org.example.chatclient.server.LoginInfo;
import org.example.chatclient.server.ServerManager;
import org.example.chatclient.textcolor.RoomDrawer;
import org.example.chatclient.textcolor.TextColor;

public class ChatClient {
    private CommandManager commandManager;
    private FileManager manager;
    private LoginInfo login;
    private InputListener listener;
    private TextColor textColor;
    private RoomDrawer drawer;

    private ServerManager serverManager;
    private static final String SERVER_ADRESS = "localhost";
    private static final int SERVER_PORT = 2147;


    public ChatClient() {
        System.out.println("Welcome User");
        System.out.println("If the console looks weird, try setting the room width using the /setwidth <number> command.");
        System.out.println("The default width is 61.");

        init();
        serverManager.connect(SERVER_ADRESS, SERVER_PORT);

        listener.listenForInput();
    }

    public void init() {
        this.drawer = new RoomDrawer(this);
        this.serverManager = new ServerManager(this);
        this.textColor = new TextColor();
        this.commandManager = new CommandManager(this);
        this.login = new LoginInfo();
        this.manager = new FileManager(login);
        this.listener = new InputListener(this, login);
        this.manager.load();
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public InputListener getInputListener() {
        return listener;
    }

    public TextColor getTextColor(){
        return textColor;
    }

    public RoomDrawer getDrawer() {
        return drawer;
    }
}
