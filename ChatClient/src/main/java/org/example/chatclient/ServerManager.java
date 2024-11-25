package org.example.chatclient;

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


    public void closeConnections() {
        try {
            running = false;
            if (socket != null) socket.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (main.getInputListener().loggedIn) main.getInputListener().loggedIn = false;
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
            if (message.equalsIgnoreCase("Welcome " + main.getInputListener().getUsername() + "!")){
                main.getInputListener().loggedIn = true;
            }
            if (checkIfMyUsername(message)){
                main.getTextColor().setTEXT(main.getTextColor().getTEXTCOLOROUT());
                main.getTextColor().setBG(main.getTextColor().getBGCOLOROUT());
            } else {
                main.getTextColor().setTEXT(main.getTextColor().getTEXTCOLORIN());
                main.getTextColor().setBG(main.getTextColor().getBGCOLORIN());
            }
            if (main.getTextColor().getBACKGROUND() == main.getTextColor().getReset() || main.getTextColor().getBACKGROUND() == main.getTextColor().getDefault())
                System.out.println(main.getTextColor().getTEXT() + message + main.getTextColor().getReset());
            else if (main.getTextColor().getTEXT() == main.getTextColor().getReset() || main.getTextColor().getTEXT() == main.getTextColor().getDefault())
                System.out.println(main.getTextColor().getBACKGROUND() + message + main.getTextColor().getReset());
            else
                System.out.println(main.getTextColor().getBACKGROUND() + main.getTextColor().getTEXT() + message + main.getTextColor().getReset());
        }
    }
}
