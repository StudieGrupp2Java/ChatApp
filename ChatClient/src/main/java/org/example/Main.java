package org.example;

import org.example.chatclient.ChatClient;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setProperty("file.encoding", "UTF-8");
        System.setOut(new PrintStream(System.out, true, "UTF8"));
        new ChatClient();
    }
}