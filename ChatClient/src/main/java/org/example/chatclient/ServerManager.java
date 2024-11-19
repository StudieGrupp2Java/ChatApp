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
                    // Check if input is available
                    if (System.in.available() > 0) {
                        String message = terminalIn.readLine();
                        if (message == null || socket.isClosed() || !running) {
                            System.out.println("Connection lost or client stopped. Exiting.");
                            break;
                        }
                        out.println(message);
                    } else {
                        // Sleep briefly to avoid busy-waiting
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading input: " + e.getMessage());
                    break;
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Server has closed the connection.");
        } catch (IOException e) {
            System.out.println("Error in connection: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnections();
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

                while (socket.isConnected()) {
                    String serverMessage = in.readLine();
                    System.out.println(serverMessage);
                }

            } catch (IOException e) {
                running = false; // Ensure the main loop knows the connection is lost
                closeConnections();
            }
        }
    }
}
