package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;

import java.util.Optional;


public class DMCommand extends Command {

    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        String roomName = "DM-Room";
        String recipient = args[0];
        String message = args[1];
        final Optional<User> optional = main.getUserManager().getUser(recipient);

        if (optional.isPresent()) {
            final User recipientUser = optional.get();
            final ConnectionHandler recipientConnection = main.getClientManager().getConnections().get(recipientUser.getIdentifier());

            roomName = roomName + main.getChatRoomManager().incrementDMNmr();
            User me = main.getUserManager().getUser(sender.getIdentifier());
            me.setRecipient(recipientUser);
            if (main.getChatRoomManager().getDmMap().isEmpty()) {
                main.getChatRoomManager().createDMRoom(recipientConnection, sender, roomName);
                main.getChatRoomManager().addUserToDMRoom(sender, recipientConnection);
                main.getClientManager().broadcastDM(me.getName() + ": " + getFullMessage(args), sender, recipientUser);
                return;
            }

            if (message.equals("join")) {
                main.getChatRoomManager().addUserToDMRoom(sender, recipientConnection);
                return;
            }
            if (!main.getChatRoomManager().getDmMap().containsKey(roomName)) {
                main.getChatRoomManager().createDMRoom(recipientConnection, sender, roomName);
                main.getChatRoomManager().addUserToDMRoom(sender, recipientConnection);
                main.getClientManager().broadcastDM(me.getName() + ": " + getFullMessage(args), sender, recipientUser);
            }
        }
    }

    private String getFullMessage(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]);
            if (i != args.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }


    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
