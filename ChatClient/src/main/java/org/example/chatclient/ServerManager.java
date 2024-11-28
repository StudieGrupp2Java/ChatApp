package org.example.chatclient;

import org.example.textcolor.RoomDrawer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

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
        return username.equals(incomingUsername);
    }

    private class ServerListener implements Runnable {
        public void run() {
            try {

                while (socket.isConnected()) {
                    String serverMessage = in.readLine();
                    if (serverMessage == null) break;

                    handleMessage(serverMessage);
                }

            } catch (IOException e) {
            } finally {
                System.out.println("Disconnected from server.");
                closeConnections();
            }
        }

        private boolean lastWasRoomMessage = false;
        private String lastRoom = "";
        private void handleMessage(String serverMessage) {
            if (serverMessage.startsWith("Joined room:")) {
                if (lastWasRoomMessage) {
                    // Finish the room box
                    System.out.println(RoomDrawer.getFooter());
                    lastWasRoomMessage = false;
                }
                String roomName = serverMessage.split("Joined room: ")[1].split(" ")[0];
                System.out.println(RoomDrawer.getHeader(roomName));
                lastRoom = roomName;
                return;
            }
            if (serverMessage.startsWith("\u00a7CHATLOGS")) {
                String[] chatlogs = serverMessage.substring(9).split("\u00a7");
                for (String log : chatlogs) {
                    System.out.println(RoomDrawer.drawBoxed(printWithColor(log)));
                    lastWasRoomMessage = true;
                }
                return;
            }
            if (serverMessage.startsWith("[")) {
                if (!lastWasRoomMessage) {
                    System.out.println(RoomDrawer.getHeader(lastRoom));
                }
                System.out.println(RoomDrawer.drawBoxed(printWithColor(serverMessage)));
                lastWasRoomMessage = true;
                return;
            } else if (lastWasRoomMessage) {
                // Finish the room box
                System.out.println(RoomDrawer.getFooter());
                lastWasRoomMessage = false;
            }

            System.out.println(printWithColor(serverMessage));
        }

        private String printWithColor(String message){
            if (!main.getInputListener().loggedIn){
                if (message.equalsIgnoreCase("Welcome " + main.getInputListener().getUsername() + "!")){
                    main.getInputListener().loggedIn = true;
                }
            }
            if (checkIfMyUsername(message)){
                main.getTextColor().setTEXT(main.getTextColor().getTEXTCOLOROUT());
                main.getTextColor().setBG(main.getTextColor().getBGCOLOROUT());
            } else {
                main.getTextColor().setTEXT(main.getTextColor().getTEXTCOLORIN());
                main.getTextColor().setBG(main.getTextColor().getBGCOLORIN());
            }
            if (main.getTextColor().getBACKGROUND() == main.getTextColor().getReset() || main.getTextColor().getBACKGROUND() == main.getTextColor().getDefault())
                return (main.getTextColor().getTEXT() + message + main.getTextColor().getReset());
            else if (main.getTextColor().getTEXT() == main.getTextColor().getReset() || main.getTextColor().getTEXT() == main.getTextColor().getDefault())
                return (main.getTextColor().getBACKGROUND() + message + main.getTextColor().getReset());
            else
                return (main.getTextColor().getBACKGROUND() + main.getTextColor().getTEXT() + message + main.getTextColor().getReset());
        }
    }
}
