package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class LeaveCommand extends Command {

    public LeaveCommand(ChatClient main) {
        super("/leave", "/leave - leaves the current server", main);
    }

    @Override
    public void execute(String[] args) {
        if (main.getServerManager().isConnected()) {
            main.getServerManager().closeConnections(); // Anropar befintlig funktion för att koppla från servern
            System.out.println("You disconnected");
        } else {
            System.out.println("You're not connected, can't disconnect");
        }
    }
}