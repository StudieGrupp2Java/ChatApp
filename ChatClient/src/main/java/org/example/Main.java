package org.example;

import org.example.chatclient.ChatClient;

public class Main {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;

        new ChatClient(serverAddress, port);
    }
}