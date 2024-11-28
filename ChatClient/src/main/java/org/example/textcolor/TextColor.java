package org.example.textcolor;

import java.util.HashMap;
import java.util.Map;

public class TextColor {

    public static final char ESC = '\u001B';
    // Reset
    public static final String RESET = ESC + "[0m";
    public static final String DEFAULT = ESC + "[39m";
    public static final String CLEAR_LINE = ESC + "[1A" + ESC + "[2K";
    public static final String CLEAR_SCREEN = ESC + "[2J";

    public static final String SAVE_CURSOR = ESC + "[s";
    public static final String MOVE_CURSOR = ESC + "[5000;5000H";
    public static final String REQUEST_CURSOR = ESC + "[6n";
    public static final String RESTORE_CURSOR = ESC + "[u";
    
    private static final Map<String, String> backgroundMap = new HashMap<>();
    private static final Map<String, String> textMap = new HashMap<>();
    private String TEXTCOLOROUT = DEFAULT;
    private String BGCOLOROUT = DEFAULT;
    private String TEXTCOLORIN = DEFAULT;
    private String BGCOLORIN = DEFAULT;
    private String TEXT = DEFAULT;
    private String BACKGROUND = DEFAULT;


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

    public void setTextColor(String input, String check) {
        String color = input.split(" ")[0];
        for (String key : textMap.keySet()){
            if (key.equalsIgnoreCase(color)){
                if (check.equalsIgnoreCase("out")){
                    TEXTCOLOROUT = textMap.get(key);
                } else if (check.equalsIgnoreCase("in")) {
                    TEXTCOLORIN = textMap.get(key);
                }
                System.out.println("Text color changed!");
                return;
            }
        }
    }

    public void setBackgroundColor(String input, String check) {
        String color = input.split(" ")[0];
        for (String key : backgroundMap.keySet()){
            if (key.equalsIgnoreCase(color)){
                if (check.equalsIgnoreCase("out")){
                    BGCOLOROUT = backgroundMap.get(key);
                } else if (check.equalsIgnoreCase("in")) {
                    BGCOLORIN = backgroundMap.get(key);
                }
                System.out.println("Background color changed!");
                return;
            }
        }
    }

    public String getTEXTCOLOROUT() {
        return TEXTCOLOROUT;
    }

    public String getBGCOLOROUT() {
        return BGCOLOROUT;
    }

    public String getTEXTCOLORIN() {
        return TEXTCOLORIN;
    }

    public String getBGCOLORIN() {
        return BGCOLORIN;
    }

    public void setTEXT(String color){
        TEXT = color;
    }

    public void setBG(String color){
        BACKGROUND = color;
    }

    public String getReset(){
        return RESET;
    }

    public String getDefault(){
        return DEFAULT;
    }

    public String getTEXT() {
        return TEXT;
    }

    public String getBACKGROUND() {
        return BACKGROUND;
    }

    public void resetTextColor(String check) {
        if (check.equalsIgnoreCase("out")) {
            TEXTCOLOROUT = DEFAULT;
            System.out.println("Text color reset!");
        }
        else if (check.equalsIgnoreCase("in")) {
            TEXTCOLORIN = DEFAULT;
            System.out.println("Text color reset!");
        } else {
            System.out.println("Invalid input!");
        }
    }

    public void resetBackgroundColor(String check) {
        if (check.equalsIgnoreCase("out")) {
            BGCOLOROUT = DEFAULT;
            System.out.println("Background color reset!");
        }
        else if (check.equalsIgnoreCase("in")){
            BGCOLORIN = DEFAULT;
            System.out.println("Background color reset!");
        } else {
            System.out.println("Invalid input!");
        }
    }
}
