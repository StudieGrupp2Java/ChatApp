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
    private BufferedReader terminalIn;
    protected boolean running = true;

    public void connect(String ip, int port) {
        try {
            System.out.println("Connecting to " + ip + ":" + port + "...");
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            terminalIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Successfully connected to the server!");

            new Thread(new ServerListener()).start();

            while (this.running) {
                try {
                    String message = terminalIn.readLine();
                    if (message == null || socket.isClosed() || !running) {
                        System.out.println("Connection lost or client stopped. Exiting.");
                        break;
                    }
                    out.println(message);
                    System.out.println("Sending message: " + message);
                } catch (IOException e) {
                    System.out.println("Error reading input: " + e.getMessage());
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error in connection: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnections();
            System.out.println("Disconnected.");
        }
    }

    private void closeConnections() {
        try {
            running = false;
            if (socket != null) socket.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (terminalIn != null) terminalIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerListener implements Runnable {
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println(serverMessage);
                }
                System.out.println("Server has closed the connection.");
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
            } finally {
                System.out.println("Disconnected.");
                System.exit(0); //TODO: disconnect normally and let user connect to new server
                running = false; // Ensure the main loop knows the connection is lost
                closeConnections();
            }
        }
    }
}
