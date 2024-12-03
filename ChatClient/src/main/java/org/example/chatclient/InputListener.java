package org.example.chatclient;

import org.example.chatclient.emoji.Emoji;
import org.example.chatclient.filemanager.FileManager;
import org.example.chatclient.server.LoginInfo;
import org.example.chatclient.textcolor.TextColor;

import java.util.Scanner;

public class InputListener {
    private Emoji emoji;
    private final ChatClient main;
    private final LoginInfo login;
    private final Scanner scan = new Scanner(System.in);
    private String username = "";
    public boolean loggedIn = false;

    public InputListener(ChatClient main, LoginInfo login) {
        this.main = main;
        this.login = login;
        emoji = new Emoji();
    }

    public void listenForInput() {
        while (scan.hasNext()) {
            String message = scan.nextLine();
            System.out.print(TextColor.CLEAR_LINE); // clear the current line, doesnt work in all terminals
            checkUsername(message);
            if (main.getCommandManager().executeCommand(message) && !message.equalsIgnoreCase("/help")) {
                continue;
            }
            checkLogin(message);
            if (validateMessage(message)) {
                continue;
            }

            for (String key : emoji.getEmojiMap().keySet()){
                if (message.contains(key)){
                    message = emoji.getEmoji(message);
                }
            }

            if (message.contains("!emoji")){
                message = emoji.emojiPicker(message, scan);
            }
            main.getServerManager().sendMessageToServer(message);
        }
    }




    private void checkUsername(String message){
        if (!loggedIn){
            if (message.contains("/login") || message.contains("/register")){
                username = message.split(" ")[1];
            }
        }
    }

    public String getUsername(){
        return username;
    }

    private void checkLogin(String message) {
        if (message.startsWith("/login")) {
            if (!login.getLoginInfo().isEmpty()) {
                return;
            }
            String[] information = message.split(" ");
            while (true) {
                System.out.println("Do you want to save the login information for automatic login later? (yes/no)");
                String choice = scan.nextLine();
                if (choice.equalsIgnoreCase("yes")) {
                    if (information.length == 3) {
                        login.addLoginInfo(information[1], information[2]);
                        System.out.println("Added login information!");
                        FileManager.save();
                        break;
                    } else {
                        System.out.println("Invalid login input!");
                    }
                } else if (choice.equalsIgnoreCase("no")) {
                    break;
                }
            }

        } else if (message.startsWith("/logout")) {
            login.removeInfo();
        }
    }

    private boolean validateMessage(String message) {
        if (message.length() > 100) {
            System.out.println("Message is too big, said she");
            return true;
        }
        return message.isEmpty();
    }


    public void automaticLogin() {
        if (!login.getLoginInfo().isEmpty()) {
            while (true) {
                System.out.println("Do you want to use saved login info? (yes/no)");
                String answer = scan.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    String[] info = login.checkLogin().split("=");
                    // tell server we're using auto-login
                    main.getServerManager().sendMessageToServer("true");
                    main.getServerManager().sendMessageToServer("/login " + info[0] + " " + info[1]);
                    username = info[0];
                    return;
                } else if (answer.equalsIgnoreCase("no")) {
                    break;
                } else {
                    System.out.println("Invalid input!");
                }
            }
        }
        // tell server we're NOT using auto-login
        main.getServerManager().sendMessageToServer("false");
    }

    public Emoji getEmoji(){
        return emoji;
    }
}
