package org.example.textcolor;

import org.example.chatclient.ServerManager;

public class TextColor {

    // Reset
    public static final String RESET = "\u001B[0m";

    // Regular Colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m"; // Also called Magenta
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Bold
    public static final String BLACK_BOLD = "\u001B[1;30m";
    public static final String RED_BOLD = "\u001B[1;31m";
    public static final String GREEN_BOLD = "\u001B[1;32m";
    public static final String YELLOW_BOLD = "\u001B[1;33m";
    public static final String BLUE_BOLD = "\u001B[1;34m";
    public static final String PURPLE_BOLD = "\u001B[1;35m";
    public static final String CYAN_BOLD = "\u001B[1;36m";
    public static final String WHITE_BOLD = "\u001B[1;37m";

    // Background Colors
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    // High-Intensity Colors
    public static final String BLACK_BRIGHT = "\u001B[90m";
    public static final String RED_BRIGHT = "\u001B[91m";
    public static final String GREEN_BRIGHT = "\u001B[92m";
    public static final String YELLOW_BRIGHT = "\u001B[93m";
    public static final String BLUE_BRIGHT = "\u001B[94m";
    public static final String PURPLE_BRIGHT = "\u001B[95m";
    public static final String CYAN_BRIGHT = "\u001B[96m";
    public static final String WHITE_BRIGHT = "\u001B[97m";

    // High-Intensity Background Colors
    public static final String BLACK_BACKGROUND_BRIGHT = "\u001B[100m";
    public static final String RED_BACKGROUND_BRIGHT = "\u001B[101m";
    public static final String GREEN_BACKGROUND_BRIGHT = "\u001B[102m";
    public static final String YELLOW_BACKGROUND_BRIGHT = "\u001B[103m";
    public static final String BLUE_BACKGROUND_BRIGHT = "\u001B[104m";
    public static final String PURPLE_BACKGROUND_BRIGHT = "\u001B[105m";
    public static final String CYAN_BACKGROUND_BRIGHT = "\u001B[106m";
    public static final String WHITE_BACKGROUND_BRIGHT = "\u001B[107m";

    private static String currentTextColor = RESET;
    private static String currentBackgroundColor = RESET;
    private final ServerManager serverManager;
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
        switch (color.toLowerCase()) {
            case "black":
                currentTextColor = BLACK;
                break;
            case "red":
                currentTextColor = RED;
                break;
            case "green":
                currentTextColor = GREEN;
                break;
            case "yellow":
                currentTextColor = YELLOW;
                break;
            case "blue":
                currentTextColor = BLUE;
                break;
            case "purple":
                currentTextColor = PURPLE;
                break;
            case "cyan":
                currentTextColor = CYAN;
                break;
            case "white":
                currentTextColor = WHITE;
                break;
            case "bright-black":
                currentTextColor = BLACK_BRIGHT;
                break;
            case "bright-red":
                currentTextColor = RED_BRIGHT;
                break;
            case "bright-green":
                currentTextColor = GREEN_BRIGHT;
                break;
            case "bright-yellow":
                currentTextColor = YELLOW_BRIGHT;
                break;
            case "bright-blue":
                currentTextColor = BLUE_BRIGHT;
                break;
            case "bright-purple":
                currentTextColor = PURPLE_BRIGHT;
                break;
            case "bright-cyan":
                currentTextColor = CYAN_BRIGHT;
                break;
            case "bright-white":
                currentTextColor = WHITE_BRIGHT;
                break;
            case "bold-black":
                currentTextColor = BLACK_BOLD;
                break;
            case "bold-red":
                currentTextColor = RED_BOLD;
                break;
            case "bold-green":
                currentTextColor = GREEN_BOLD;
                break;
            case "bold-yellow":
                currentTextColor = YELLOW_BOLD;
                break;
            case "bold-blue":
                currentTextColor = BLUE_BOLD;
                break;
            case "bold-purple":
                currentTextColor = PURPLE_BOLD;
                break;
            case "bold-cyan":
                currentTextColor = CYAN_BOLD;
                break;
            case "bold-white":
                currentTextColor = WHITE_BOLD;
                break;
            case "reset":
                currentTextColor = RESET;
                break;
            default:
                System.out.println("Invalid text color.");
        }
        serverManager.setColor(currentTextColor, currentBackgroundColor);
    }

    private void setBackgroundColor(String color) {
        switch (color.toLowerCase()) {
            case "black":
                currentBackgroundColor = BLACK_BACKGROUND;
                break;
            case "red":
                currentBackgroundColor = RED_BACKGROUND;
                break;
            case "green":
                currentBackgroundColor = GREEN_BACKGROUND;
                break;
            case "yellow":
                currentBackgroundColor = YELLOW_BACKGROUND;
                break;
            case "blue":
                currentBackgroundColor = BLUE_BACKGROUND;
                break;
            case "purple":
                currentBackgroundColor = PURPLE_BACKGROUND;
                break;
            case "cyan":
                currentBackgroundColor = CYAN_BACKGROUND;
                break;
            case "white":
                currentBackgroundColor = WHITE_BACKGROUND;
                break;
            case "bright-black":
                currentBackgroundColor = BLACK_BACKGROUND_BRIGHT;
                break;
            case "bright-red":
                currentBackgroundColor = RED_BACKGROUND_BRIGHT;
                break;
            case "bright-green":
                currentBackgroundColor = GREEN_BACKGROUND_BRIGHT;
                break;
            case "bright-yellow":
                currentBackgroundColor = YELLOW_BACKGROUND_BRIGHT;
                break;
            case "bright-blue":
                currentBackgroundColor = BLUE_BACKGROUND_BRIGHT;
                break;
            case "bright-purple":
                currentBackgroundColor = PURPLE_BACKGROUND_BRIGHT;
                break;
            case "bright-cyan":
                currentBackgroundColor = CYAN_BACKGROUND_BRIGHT;
                break;
            case "bright-white":
                currentBackgroundColor = WHITE_BACKGROUND_BRIGHT;
                break;
            case "reset":
                currentBackgroundColor = RESET;
                break;
            default:
                System.out.println("Invalid background color.");
        }
        serverManager.setColor(currentTextColor, currentBackgroundColor);
    }

    private void resetTextColor() {
        currentTextColor = RESET;
        currentTextColor += currentBackgroundColor;
        serverManager.setColor(currentTextColor, currentBackgroundColor);
    }

    private void resetBackgroundColor() {
        currentBackgroundColor = RESET;
        serverManager.setColor(currentTextColor, currentBackgroundColor);
    }
}
