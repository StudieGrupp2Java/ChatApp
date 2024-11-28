package org.example.chatclient;


import org.example.InputListener;
import org.example.commandmanager.CommandManager;
import org.example.filemanager.FileManager;
import org.example.logininfo.LoginInfo;
import org.example.textcolor.RoomDrawer;
import org.example.textcolor.TextColor;

import java.io.IOException;

public class ChatClient {
    private CommandManager commandManager;
    private FileManager manager;
    private LoginInfo login;
    private InputListener listener;
    private TextColor textColor;

    private ServerManager serverManager;
    private static final String SERVER_ADRESS = "localhost";
    private static final int SERVER_PORT = 2147;


    public ChatClient() {
        System.out.println("Welcome User");
        init();
        serverManager.connect(SERVER_ADRESS, SERVER_PORT);

        listener.listenForInput();
    }

    public void init() {
        this.serverManager = new ServerManager(this);
        textColor = new TextColor();
        this.commandManager = new CommandManager(this);
        this.login = new LoginInfo();
        this.manager = new FileManager(login);
        this.listener = new InputListener(this, login);
        manager.load();
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

}
