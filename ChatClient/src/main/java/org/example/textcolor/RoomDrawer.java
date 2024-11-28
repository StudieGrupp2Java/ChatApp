package org.example.textcolor;


import java.io.IOException;

public class RoomDrawer {
    public static final String DOUBLE_HORIZ = "\u2550";
    public static final String DOUBLE_VERT = "\u2551";
    public static final String DOUBLE_TOP_LEFT = "\u2554";
    public static final String DOUBLE_TOP_RIGHT = "\u2557";
    public static final String DOUBLE_BOTTOM_LEFT = "\u255A";
    public static final String DOUBLE_BOTTOM_RIGHT = "\u255D";

    private static final int X = 85;
    private static final int Y = 32;
    

    public static String getFooter() {
        int width = X;
        StringBuilder builder = new StringBuilder();
        builder.append(DOUBLE_BOTTOM_LEFT);
        builder.append(DOUBLE_HORIZ.repeat(width-2));
        builder.append(DOUBLE_BOTTOM_RIGHT);
        return builder.toString();
    }

    public static int[] getTerminalSize() throws IOException {
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

    public static String getHeader(String roomName) {
        int width = X;

        boolean isEven = roomName.length() % 2 == 0;
        int totalWidth = width - roomName.length() - 2;

        int repeat = totalWidth / 2;

        StringBuilder header = new StringBuilder();
        header.append(DOUBLE_TOP_LEFT);
        header.append(DOUBLE_HORIZ.repeat((isEven ? 1 + repeat : repeat)));
        header.append(roomName);
        header.append(DOUBLE_HORIZ.repeat(repeat));
        header.append(DOUBLE_TOP_RIGHT);
        return header.toString();
    }

    public static String drawBoxed(String log) {
        String message = DOUBLE_VERT + " " + log;

        int missing = X - message.length() - 2;
        if (missing > 0) {
            message += " ".repeat(missing);
        }
        message += " " + DOUBLE_VERT;
        return message;
    }
}
