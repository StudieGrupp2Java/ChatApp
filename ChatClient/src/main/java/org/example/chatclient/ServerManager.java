package org.example.chatclient;

import org.example.emoji.Emoji;
import org.example.textcolor.TextColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerManager {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    protected boolean running = true;
    private final ChatClient main;
    private final String RESET = "\u001B[0m";
    private final String DEFAULT = "\u001B[39m";
    private String TEXTCOLOROUT = DEFAULT;
    private String BGCOLOROUT = DEFAULT;
    private String TEXTCOLORIN = DEFAULT;
    private String BGCOLORIN = DEFAULT;
    private String TEXT = DEFAULT;
    private String BACKGROUND = DEFAULT;




    public ServerManager(ChatClient main){
        this.main = main;
    }

    public void connect(String ip, int port) {
        try {
            running = true;
            System.out.println("Connecting to " + ip + ":" + port + "...");
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Successfully connected to the server!");
            main.getInputListener().automaticLogin();
            new Thread(new ServerListener()).start();

            //System.out.println("Server has closed the connection.");
        } catch (IOException e) {
            System.out.println("Error in connection: " + e.getMessage());
            e.printStackTrace();
            closeConnections();
        }
    }

    public void sendMessageToServer(String message) {
        if (message == null || socket.isClosed() || !running) {
            System.out.println("Tried to send message while server is closed.");
            return;
        }

        out.println(message);
    }

    public void setColorOut(String textColor, String backgroundColor){
        TEXTCOLOROUT = textColor;
        BGCOLOROUT = backgroundColor;
    }
    public void setColorIn(String textColor, String backgroundColor){
        TEXTCOLORIN = textColor;
        BGCOLORIN = backgroundColor;
    }

    public void closeConnections() {
        try {
            running = false;
            if (socket != null) socket.close();
            if (out != null) out.close();
            if (in != null) in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return running;
    }

    private boolean checkIfMyUsername(String message){
        String incomingUsername = "";
        String username = main.getInputListener().getUsername();
        String[] split = message.split("] ");
        if (split.length > 1){
            incomingUsername = split[1].split(": ")[0];
        }
        if (username.equals(incomingUsername)){
            return true;
        }
        return false;
    }

    private class ServerListener implements Runnable {
        public void run() {
            try {

                while (socket.isConnected()) {
                    String serverMessage = in.readLine();
                    if (serverMessage == null) break;
                    printWithColor(serverMessage);
                }

            } catch (IOException e) {
            } finally {
                closeConnections();
            }
        }

        private void printWithColor(String message){
            if (checkIfMyUsername(message)){
                TEXT = TEXTCOLOROUT;
                BACKGROUND = BGCOLOROUT;
            } else {
                TEXT = TEXTCOLORIN;
                BACKGROUND = BGCOLORIN;
            }
            if (BACKGROUND == RESET || BACKGROUND == DEFAULT)
                System.out.println(TEXT + message + RESET);
            else if (TEXT == RESET || TEXT == DEFAULT)
                System.out.println(BACKGROUND + message + RESET);
            else
                System.out.println(BACKGROUND + TEXT + message + RESET);
        }
    }
}
