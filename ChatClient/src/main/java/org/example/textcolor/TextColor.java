package org.example.textcolor;

import org.example.chatclient.ServerManager;

import java.util.HashMap;
import java.util.Map;

public class TextColor {

    // Reset
    public static final String RESET = "\u001B[0m";
    private static String currentTextColor = RESET;
    private static String currentBackgroundColor = RESET;
    private final ServerManager serverManager;
    public static final String DEFAULT = "\u001B[39m";
    private static final Map<String, String> backgroundMap = new HashMap<>();
    private static final Map<String, String> textMap = new HashMap<>();
    static
    {
        backgroundMap.put("bright-black", "\u001B[100m");
        backgroundMap.put("bright-red", "\u001B[101m");
        backgroundMap.put("bright-green", "\u001B[102m");
        backgroundMap.put("bright-yellow", "\u001B[103m");
        backgroundMap.put("bright-blue", "\u001B[104m");
        backgroundMap.put("bright-purple", "\u001B[105m");
        backgroundMap.put("bright-cyan", "\u001B[106m");
        backgroundMap.put("bright-white", "\u001B[107m");
        backgroundMap.put("black", "\u001B[40m");
        backgroundMap.put("red", "\u001B[41m");
        backgroundMap.put("green", "\u001B[42m");
        backgroundMap.put("yellow", "\u001B[43m");
        backgroundMap.put("blue", "\u001B[44m");
        backgroundMap.put("purple", "\u001B[45m");
        backgroundMap.put("cyan", "\u001B[46m");
        backgroundMap.put("white", "\u001B[47m");
        textMap.put("black", "\u001B[30m");
        textMap.put("red", "\u001B[31m");
        textMap.put("green", "\u001B[32m");
        textMap.put("yellow", "\u001B[33m");
        textMap.put("blue", "\u001B[34m");
        textMap.put("purple", "\u001B[35m");
        textMap.put("cyan", "\u001B[36m");
        textMap.put("white", "\u001B[37m");
        textMap.put("bold-black", "\u001B[1;30m");
        textMap.put("bold-red", "\u001B[1;31m");
        textMap.put("bold-green", "\u001B[1;32m");
        textMap.put("bold-yellow", "\u001B[1;33m");
        textMap.put("bold-blue", "\u001B[1;34m");
        textMap.put("bold-purple", "\u001B[1;35m");
        textMap.put("bold-cyan", "\u001B[1;36m");
        textMap.put("bold-white", "\u001B[1;37m");
        textMap.put("bright-black", "\u001B[90m");
        textMap.put("bright-red", "\u001B[91m");
        textMap.put("bright-green", "\u001B[92m");
        textMap.put("bright-yellow", "\u001B[93m");
        textMap.put("bright-blue", "\u001B[94m");
        textMap.put("bright-purple", "\u001B[95m");
        textMap.put("bright-cyan", "\u001B[96m");
        textMap.put("bright-white", "\u001B[97m");
    }

    public TextColor(ServerManager serverManager){
        this.serverManager = serverManager;
    }

    public boolean handleInput(String input) {
        if (input.startsWith("/setcolor ")) {
            String color = input.substring(10).trim();
            setTextColor(color);
            return true;
        } else if (input.startsWith("/setbg ")) {
            String color = input.substring(7).trim();
            setBackgroundColor(color);
            return true;
        } else if (input.equals("/resetcolor")) {
            resetTextColor();
            return true;
        } else if (input.equals("/resetbg")) {
            resetBackgroundColor();
            return true;
        }
        return false;
    }

    private void setTextColor(String color) {
        for (String key : textMap.keySet()){
            if (key.equalsIgnoreCase(color)){
                currentTextColor = textMap.get(key);
                serverManager.setColor(currentTextColor, currentBackgroundColor);
                return;
            }
        }
    }

    private void setBackgroundColor(String color) {
        for (String key : backgroundMap.keySet()){
            if (key.equalsIgnoreCase(color)){
                currentBackgroundColor = backgroundMap.get(key);
                serverManager.setColor(currentTextColor, currentBackgroundColor);
                return;
            }
        }
    }

    private void resetTextColor() {
        currentTextColor = DEFAULT;
        serverManager.setColor(currentTextColor, currentBackgroundColor);
    }

    private void resetBackgroundColor() {
        currentBackgroundColor = DEFAULT;
        serverManager.setColor(currentTextColor, currentBackgroundColor);
    }
}
