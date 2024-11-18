package org.example.handling;

import org.example.ChatServer;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

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
                    String fullMessage = String.format("[%s] %s", user.getName(), main.getChatFilter().filterMessage(incomingMessage));
                    user.getHandler().sendMessage(fullMessage);
                });

            }
        } catch (IOException e) {
            System.err.println("Error in reading/writing to connection");
            e.printStackTrace();
        } finally {
            try {
                main.getUserManager().removeUser(this);
            } catch (IOException e) {
                System.err.println("Error removing user");
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String incomingMessage) {
        this.out.println(incomingMessage);
    }

    public void close() {
        this.running = false;
        try {
            this.getSocket().close();
            this.in.close();
            this.out.close();
        } catch (IOException e) {
            System.err.println("Error closing connection");
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionHandler that = (ConnectionHandler) o;
        return running == that.running && Objects.equals(socket, that.socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, running);
    }
}
