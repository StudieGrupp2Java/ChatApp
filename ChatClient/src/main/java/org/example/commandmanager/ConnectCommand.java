package org.example.commandmanager;

import org.example.chatclient.ChatClient;


public class ConnectCommand extends Command {

    public ConnectCommand(ChatClient main) {
        super("/connect", main);
    }

    @Override
    public void execute(String[] args) {
        if (main.getServerManager().isConnected()){
            System.out.println("You're already connected to a server. First disconnect with /leave, then you can connect to a new server.");
            return;
        }

        try {
            final String ip = args[1];
            final int port = Integer.parseInt(args[2]);
            main.getServerManager().connect(ip, port);
        } catch (Exception e){
            System.out.println("Couldn't connect to server: " + e.getMessage());
        }
    }
}
