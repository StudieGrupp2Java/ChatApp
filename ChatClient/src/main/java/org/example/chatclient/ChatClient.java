package org.example.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader termnialIn;

    public ChatClient(String adress, int portNmbr){
        try{
            socket = new Socket(adress, portNmbr);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            termnialIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Successfully connected to the server!");

            new Thread(new ServerListener()).start();

            String message;
            while ((message = termnialIn.readLine()) != null){
                out.println(message);
                System.out.println("Sending message " + message);
            }
        } catch (IOException e){
            System.out.println("opsi");
        }
    }

    private void closeConnections() {
        try {
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
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        }
    }
}
