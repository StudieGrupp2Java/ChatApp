package org.example.commandmanager;

import org.example.chatclient.ChatClient;

public class LeaveCommand extends Command {

    public LeaveCommand(ChatClient main) {
        super("/leave", main);
    }

    @Override
    public void execute(String[] args) {
        if (main.getServerManager().isConnected()) {
            main.getServerManager().closeConnections(); // Anropar befintlig funktion för att koppla från servern
            System.out.println("You disconnected");
            main.getInputListener().loggedIn = false;
        } else {
            System.out.println("You're not connected, can't disconnect");
        }
    }
}