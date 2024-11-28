package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.util.Util;

public class OnlineCommand extends Command {
    // TODO: replace with TextColor reference once merged
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";
    private static final String WHITE = "\u001B[37m";
    private static final String RED = "\u001B[31m";


    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        StringBuilder online = new StringBuilder("ONLINE: ");
        main.getUserManager().getUsers()
                .stream().filter(user -> user.getStatus().equals(User.Status.ONLINE))
                .forEach(user -> {
                    online.append(GREEN).append(user.getName()).append(RESET).append(", ");
                });
        // Remove last comma
        if (online.length() > "ONLINE: ".length()) {
            online.setLength(online.length() - 2);
        }

        sender.sendMessage(online.toString());

        StringBuilder away = new StringBuilder("AWAY: ");
        main.getUserManager().getUsers()
                .stream().filter(user -> user.getStatus().equals(User.Status.AWAY))
                .forEach(user -> {
                    String timeSince = Util.timeSince(user.getLastSeen());

                    away.append(WHITE)
                            .append(user.getName())
                            .append(" (last seen ")
                            .append(timeSince)
                            .append(")")
                            .append(RESET)
                            .append(", ");
                });
        // Remove last comma
        if (away.length() > "AWAY: ".length()) {
            away.setLength(away.length() - 2);
        }

        sender.sendMessage(away.toString());


        StringBuilder offline = new StringBuilder("OFFLINE: ");
        main.getUserManager().getUsers()
                .stream().filter(user -> user.getStatus().equals(User.Status.OFFLINE))
                .forEach(user -> {
                    String timeSince = Util.timeSince(user.getLastSeen());

                    offline.append(RED)
                            .append(user.getName())
                            .append(" (last seen ")
                            .append(timeSince)
                            .append(")")
                            .append(RESET)
                            .append(", ");
                });
        // Remove last comma
        if (offline.length() > "OFFLINE: ".length()) {
            offline.setLength(offline.length() - 2);
        }

        sender.sendMessage(offline.toString());
    }

    @Override
    protected int getExpectedArgsCount() {
        return 0;
    }

    @Override
    public ChatRole getPermissionLevel() {
        return ChatRole.USER;
    }
}
