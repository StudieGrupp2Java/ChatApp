package org.example.chatclient;


public class ChatClient {

    private ServerManager serverManager;
    private static final String SERVER_ADRESS = "localhost";
    private static final int SERVER_PORT = 2147;


    public ChatClient() {
        System.out.println("Welcome User");

        init();

        serverManager.connect(SERVER_ADRESS, SERVER_PORT);
    }

    public void init() {
        this.serverManager = new ServerManager();
    }

}
