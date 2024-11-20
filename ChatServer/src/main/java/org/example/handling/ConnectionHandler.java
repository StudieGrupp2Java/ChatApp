package org.example.handling;

import org.example.ChatServer;
import org.example.users.User;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class ConnectionHandler extends Thread {
    private final int identifier;
    private final ChatServer main;
    private final Socket socket;
    private boolean running = true;

    private final BufferedReader in;
    private final PrintWriter out;

    public ConnectionHandler(ChatServer main, Socket socket) {
        this.identifier = UUID.randomUUID().hashCode();
        this.main = main;
        this.socket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                if (isCommand(incomingMessage)) {
                    main.getCommandManager().handleIncomingCommand(incomingMessage, this);
                    continue;
                }

                final User sender = main.getUserManager().getUser(this.getIdentifier());
                if (sender == null) {
                    // has not authenticated, tell to authenticate
                    this.sendMessage("You're not authenticated. Register with /register or login with /login");
                    continue;
                }
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                main.getFileInfo().addMessage(sender.getName(), incomingMessage);
                main.getClientManager().getConnections().forEach(connection -> {
                    String name = sender.getName();
                    String fullMessage = String.format("[%s] %s: %s", format.format(System.currentTimeMillis()), name, main.getChatFilter().filterMessage(incomingMessage));
                    connection.sendMessage(fullMessage);
                });

            }
        } catch (IOException e) {
            System.err.println("Error in reading/writing to connection");
            e.printStackTrace();
        } finally {
            main.getClientManager().removeConnection(this);
        }
    }

    public PrintWriter getWriter(){
        return out;
    }

    private boolean isCommand(String message) {
        return message.startsWith("/") || message.startsWith("@");
    }

    public void sendMessage(String incomingMessage) {
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

    public int getIdentifier() {
        return identifier;
    }
}
