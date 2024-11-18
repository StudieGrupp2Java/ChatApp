package org.example.handling;

import org.example.ChatServer;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientManager {
    private static int SERVER_PORT = 2147; //TODO: changeable
    private final ChatServer main;

    public ClientManager(ChatServer chatServer) {
        this.main = chatServer;
    }

    public void listen() {
        try (ServerSocket socket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started on port " + SERVER_PORT);
            System.out.println("Listening...");


            while (true) {
                try {
                    ConnectionHandler handler = new ConnectionHandler(main, socket.accept());
                    main.getUserManager().addUser(handler);
                } catch (IOException e) {
                    System.err.println("Error handling new connection");
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            System.err.println("Error starting ChatServer");
            e.printStackTrace();
        }

    }
}
