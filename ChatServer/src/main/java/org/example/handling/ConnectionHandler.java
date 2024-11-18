package org.example.handling;

import org.example.ChatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private ChatServer main;
    private final Socket socket;
    private boolean running = true;

    private DataInputStream in;
    private DataOutputStream out;

    public ConnectionHandler(ChatServer main, Socket socket) {
        this.main = main;
        this.socket = socket;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Error initializing new connection");
        }
    }

    @Override
    public void run() {

        try {
            while (this.running) {
                String incomingMessage = in.readUTF();
                main.getUserManager().getUsers().forEach(user -> {
                    String fullMessage = String.format("[%s] %s", user.getName(), incomingMessage);
                    user.getHandler().sendMessage(fullMessage);
                });

            }
        } catch (IOException e) {
            System.err.println("Error in reading/writing to connection");
            e.printStackTrace();
        }
    }

    private void sendMessage(String incomingMessage) {
        try {
            this.out.writeUTF(incomingMessage);
            this.out.flush();
        } catch (IOException e) {
            System.err.println("Error in writing");
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return socket;
    }
}
