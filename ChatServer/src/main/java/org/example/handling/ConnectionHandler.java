package org.example.handling;

import org.example.ChatServer;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.util.TextColor;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.UUID;

public class ConnectionHandler extends Thread {
    private int identifier;
    private final ChatServer main;
    private final Socket socket;
    private boolean running = true;

    private final BufferedReader in;
    private final PrintWriter out;

    public ConnectionHandler(ChatServer main, Socket socket) {
        this.identifier = -Math.abs(UUID.randomUUID().hashCode()); // ensure negative identifier on initialization (logged out)
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
            // No auto login.
            if (in.readLine().equals("false")) {
                this.sendMessage("Please register with /register <username> <password> or login with /login <username> <password>");
            }
            while (this.running) {
                String incomingMessage = in.readLine();
                if (incomingMessage == null) {
                    // user disconnected
                    break;
                }

                // User has sent something, reset their last-seen
                main.getUpdateTracker().updateStatus(this.identifier);

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

                final String fullMessage = formatMessage(sender, incomingMessage);

                // Send to the current room
                if (sender.getCurrentRoom() != null) {
                    main.getClientManager().broadcastMessageInRoom(fullMessage, true, sender);
                } else {
                    this.sendMessage("Need to join a chat room first! /help for more information");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in reading/writing to connection");
            e.printStackTrace();
        } finally {
            main.getClientManager().removeConnection(this);
        }
    }

    private String formatMessage(User sender, String incomingMessage) {
        return String.format(
                "%s%s%s: %s",
                colorFromRole(sender.getRole()),
                sender.getName(),
                TextColor.RESET,
                main.getChatFilter().filterMessage(incomingMessage));
    }

    private String colorFromRole(ChatRole role) {
        switch (role) {
            case USER -> {
                return TextColor.GREEN;
            }
            case ADMIN -> {
                return TextColor.BOLD + TextColor.RED;
            }
        }
        return "";
    }


    private boolean isCommand(String message) {
        return message.startsWith("/") || message.startsWith("@");
    }

    public synchronized void sendMessage(String incomingMessage) {
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

    private void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void logout() {
        this.setIdentifier(-Math.abs(this.getIdentifier()));
    }

    public void login(User user) {
        this.setIdentifier(user.getIdentifier());
    }

    /**
     * Logged in connections have positive identifiers
     */
    public boolean isLoggedIn() {
        return identifier >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionHandler that = (ConnectionHandler) o;
        return identifier == that.identifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
