package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.Command;
import org.example.handling.ConnectionHandler;
import org.example.users.ChatRole;
import org.example.users.User;
import org.example.util.TextColor;
import org.example.util.Util;

public class OnlineCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        StringBuilder online = new StringBuilder("ONLINE: ");
        main.getUserManager().getUsers()
                .stream().filter(user -> user.getStatus().equals(User.Status.ONLINE))
                .forEach(user -> {
                    online.append(TextColor.GREEN).append(user.getName()).append(TextColor.RESET).append(", ");
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

                    away.append(TextColor.WHITE)
                            .append(user.getName())
                            .append(" (last seen ")
                            .append(timeSince)
                            .append(")")
                            .append(TextColor.RESET)
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

                    offline.append(TextColor.RED)
                            .append(user.getName())
                            .append(" (last seen ")
                            .append(timeSince)
                            .append(")")
                            .append(TextColor.RESET)
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

    @Override
    public String getUsage() {
        return "/online - lists every online user and their status";
    }
}
