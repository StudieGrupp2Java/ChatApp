package org.example;

import org.example.filter.ChatFilter;
import org.example.util.FileInformationHandler;

public class Main {

    public static final FileInformationHandler fileInfo = new FileInformationHandler();
    public static final ChatFilter chatFilter = new ChatFilter(fileInfo);
    public static void main(String[] args) {
        new ChatServer();
    }
}