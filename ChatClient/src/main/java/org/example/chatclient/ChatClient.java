package org.example.chatclient;


import org.example.filemanager.FileManager;
import org.example.logininfo.LoginInfo;

public class ChatClient {
    private FileManager manager;
    private final LoginInfo login;
    private ServerManager serverManager;
    private static final String SERVER_ADRESS = "localhost";
    private static final int SERVER_PORT = 2147;


    public ChatClient() {
        login = new LoginInfo();
        manager = new FileManager(login);
        System.out.println("Welcome User");

        init();
        FileManager.load();
        serverManager.connect(SERVER_ADRESS, SERVER_PORT);
    }

    public void init() {
        this.serverManager = new ServerManager(login);
    }


}
