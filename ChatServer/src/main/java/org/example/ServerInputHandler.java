package org.example;

import java.util.Scanner;

public class ServerInputHandler {
    private final ChatServer main;

    public ServerInputHandler(ChatServer main) {
        this.main = main;
    }

    public void listenForInput() {
        final Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (scanner.hasNext()) {
                final String message = scanner.nextLine();

                main.getCommandManager().handleServerCommand(message);
            }
        }).start();
    }
}
