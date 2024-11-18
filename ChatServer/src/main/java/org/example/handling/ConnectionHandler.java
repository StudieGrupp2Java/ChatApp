package org.example.handling;

import org.example.ChatServer;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private ChatServer main;
    private final Socket socket;
    private boolean running = true;

    private BufferedReader in;
    private PrintWriter out;

    public ConnectionHandler(ChatServer main, Socket socket) {
        this.main = main;
        this.socket = socket;

        try {
            in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("Error initializing new connection");
        }
    }

    @Override
    public void run() {

        try {
            while (this.running) {
                String incomingMessage = in.readLine();
                System.out.println(incomingMessage);
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
        this.out.println(incomingMessage);
    }

    public Socket getSocket() {
        return socket;
    }
}
