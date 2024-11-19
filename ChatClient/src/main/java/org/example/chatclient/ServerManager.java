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
    private BufferedReader termnialIn;
    protected boolean running = true;

    public void connect(String ip, int port) {
        try {
            System.out.println("Connecting to " + ip + ":" + port + "...");
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            termnialIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Successfully connected to the server!");

            new Thread(new ServerListener()).start();

            while (this.running) {
                final String message = termnialIn.readLine();
                out.println(message);
                System.out.println("Sending message " + message);
            }

        } catch (IOException e) {
            System.out.println("Error in connection");
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
            if (termnialIn != null) termnialIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerListener implements Runnable {
        public void run() {
            try {
                while (running) {
                    System.out.println(in.readLine());
                }
            } catch (IOException e) {
                closeConnections();
                System.out.println("Disconnected.");
            }
        }
    }
}
