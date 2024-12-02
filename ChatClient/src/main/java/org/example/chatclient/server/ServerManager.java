package org.example.chatclient.server;

import org.example.chatclient.ChatClient;
import org.example.chatclient.encryption.PasswordEncrypter;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ServerManager {
    private final ChatClient main;
    protected boolean running = true;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


    public ServerManager(ChatClient main) {
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
        // Strip ANSI control codes
        message = message.replaceAll("\\033\\[[0-9;]*[a-zA-Z]", "");

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
                String publicKey64 = in.readLine();
                out.println(PasswordEncrypter.encryptKey(publicKey64));
                while (socket.isConnected()) {
                    String serverMessage = in.readLine();
                    if (serverMessage == null) break;

                    if (serverMessage.startsWith("/playSound")) {
                        String notifikationType = serverMessage.split(" ")[1];
                        playNotificationSound(notifikationType);
                    } else {
                        printWithColor(serverMessage);
                    }
                }
            } catch (Exception e) {
            } finally {
                System.out.println("Disconnected from server.");
                closeConnections();
            }
        }

        private void printWithColor(String message) {
            if (!main.getInputListener().loggedIn) {
                if (message.equalsIgnoreCase("Welcome " + main.getInputListener().getUsername() + "!")) {
                    main.getInputListener().loggedIn = true;
                }
            }
            if (checkIfMyUsername(message)) {
                main.getTextColor().setTEXT(main.getTextColor().getTEXTCOLOROUT());
                main.getTextColor().setBG(main.getTextColor().getBGCOLOROUT());

                message = message.replaceAll("\u001B\\[0m", "\u001B[0m" + main.getTextColor().getTEXTCOLOROUT() + main.getTextColor().getBGCOLOROUT());
            } else {
                main.getTextColor().setTEXT(main.getTextColor().getTEXTCOLORIN());
                main.getTextColor().setBG(main.getTextColor().getBGCOLORIN());

                message = message.replaceAll("\u001B\\[0m", "\u001B[0m" + main.getTextColor().getTEXTCOLORIN() + main.getTextColor().getBGCOLORIN());
            }
            if (main.getTextColor().getBACKGROUND() == main.getTextColor().getReset() || main.getTextColor().getBACKGROUND() == main.getTextColor().getDefault())
                System.out.println(main.getTextColor().getTEXT() + message + main.getTextColor().getReset());
            else if (main.getTextColor().getTEXT() == main.getTextColor().getReset() || main.getTextColor().getTEXT() == main.getTextColor().getDefault())
                System.out.println(main.getTextColor().getBACKGROUND() + message + main.getTextColor().getReset());
            else
                System.out.println(main.getTextColor().getBACKGROUND() + main.getTextColor().getTEXT() + message + main.getTextColor().getReset());
        }
    }

    private void playNotificationSound(String notificationType) {
    String soundFilePath;
    if (notificationType.equals("message")) {
        soundFilePath = "sounds/message_notification.wav";
    } else if (notificationType.equals("dm")) {
        soundFilePath = "sounds/dm_notification.wav";
    } else {
        return; // Okänt notifikationskommando
    }

    try {
        Clip clip = AudioSystem.getClip();
        // Lägg till Objects.requireNonNull() för att säkerställa att resursen finns
        clip.open(AudioSystem.getAudioInputStream(
            Objects.requireNonNull(
                this.getClass().getClassLoader().getResource(soundFilePath),
                "Sound file not found: " + soundFilePath
            )
        ));
        clip.start();
    } catch (Exception e) {
        System.err.println("Error playing sound: " + e.getMessage());
    }
}

}
