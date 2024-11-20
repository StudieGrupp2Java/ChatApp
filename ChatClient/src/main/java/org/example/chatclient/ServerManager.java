package org.example.chatclient;

import org.example.filemanager.FileManager;
import org.example.logininfo.LoginInfo;

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
    private final LoginInfo login;

    public ServerManager(LoginInfo login){
        this.login = login;
    }

    public void connect(String ip, int port) {
        try {
            System.out.println("Connecting to " + ip + ":" + port + "...");
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            terminalIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Successfully connected to the server!");
            automaticLogin();
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
                        checkLogin(message);
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

    private void checkLogin(String message){
        if (message.startsWith("/login")){
            if (!login.getLoginInfo().isEmpty()){
                return;
            }
            String[] information = message.split(" ");
            if (information.length == 3){
                login.addLoginInfo(information[1], information[2]);
                System.out.println("Added login information!");
                FileManager.save();
            }
        } else if (message.startsWith("/logout")){
            login.removeInfo();
        }
    }

    private void automaticLogin() throws IOException {
        if (!login.getLoginInfo().isEmpty()){
            while(true){
                System.out.println("Do you want to use saved login info? (yes/no)");
                String answer = terminalIn.readLine();
                if (answer.equalsIgnoreCase("yes")){
                    String[] info = login.checkLogin().split("=");
                    out.println("/login " + info[0] + " " + info[1]);
                    break;
                } else if (answer.equalsIgnoreCase("no")){
                    break;
                } else {
                    System.out.println("Invalid input!");
                }
            }

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
