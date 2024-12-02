package org.example.chatclient.textcolor;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomDrawer {
    public static final String DOUBLE_HORIZ = "\u2550";
    public static final String DOUBLE_VERT = "\u2551";
    public static final String DOUBLE_TOP_LEFT = "\u2554";
    public static final String DOUBLE_TOP_RIGHT = "\u2557";
    public static final String DOUBLE_BOTTOM_LEFT = "\u255A";
    public static final String DOUBLE_BOTTOM_RIGHT = "\u255D";

    private int width = 61;


    public void setWidth(int width) {
        this.width = width;
    }

    public String getFooter() {
        StringBuilder builder = new StringBuilder();
        builder.append(DOUBLE_BOTTOM_LEFT);
        builder.append(DOUBLE_HORIZ.repeat(width - 2));
        builder.append(DOUBLE_BOTTOM_RIGHT);
        return builder.toString();
    }


    // does not work :(
    public int[] getTerminalSize() throws IOException {
        // Save the current cursor position
        System.out.print(TextColor.SAVE_CURSOR);
        // Move cursor to a very large row and column
        System.out.print(TextColor.MOVE_CURSOR);

        // Request the cursor position
        System.out.print(TextColor.REQUEST_CURSOR);
        System.out.flush();

        // Restore the cursor position
        System.out.print(TextColor.RESTORE_CURSOR);
        System.out.flush();

        // Read the response from the terminal
        byte[] buffer = new byte[1024];
        int bytesRead = System.in.read(buffer);

        if (bytesRead > 0) {
            String response = new String(buffer, 0, bytesRead);
            if (response.matches("\u001b\\[(\\d+);(\\d+)R")) {
                // Extract the rows and columns using regex
                String[] parts = response.substring(2, response.length() - 1).split(";");
                int rows = Integer.parseInt(parts[0]);
                int cols = Integer.parseInt(parts[1]);

                return new int[]{rows, cols};
            } else {
                throw new IOException("Unexpected response: " + response);
            }
        } else {
            throw new IOException("No response from terminal.");
        }
    }

    public String getHeader(String roomName) {
        int totalWidth = width - roomName.length() - 2;

        int repeat = totalWidth / 2;

        StringBuilder header = new StringBuilder();
        header.append(DOUBLE_TOP_LEFT);
        header.append(DOUBLE_HORIZ.repeat(repeat));
        header.append(roomName);
        header.append(DOUBLE_HORIZ.repeat(repeat));
        header.append(DOUBLE_TOP_RIGHT);

        if (header.length() != width) {
            header.insert(1, DOUBLE_HORIZ.charAt(0));
        }
        return header.toString();
    }

    public String drawBoxed(String log) {
        String extra = "";
        // Strip ANSI control codes
        int length = log.replaceAll("\\033\\[[0-9;]*[a-zA-Z]", "").length();
        int lengthDiff = log.length() - length;

        String message = DOUBLE_VERT + " " + log;

        int missing = width - length - 4; // 4 = 1 character padding + vertical line character on each side
        if (missing > 0) {
            message += " ".repeat(missing);
        } else {
            extra = message.substring(width + lengthDiff - 6);
            message = message.substring(0, width + lengthDiff - 6);
        }
        message += " " + DOUBLE_VERT;
        if (!extra.isBlank()) {
            message += "\n" + drawBoxed(extra);
        }
        return message;
    }
}
