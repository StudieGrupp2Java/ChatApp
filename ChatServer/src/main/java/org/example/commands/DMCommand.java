package org.example.commands;

import org.example.ChatServer;
import org.example.handling.ConnectionHandler;
import org.example.users.User;


public class DMCommand extends Command {
    ChatServer main;
    private int dmRoomCount = 1;
    String roomName = "DM-Room" + dmRoomCount;
    @Override
    protected void execute(String[] args, ChatServer main, ConnectionHandler sender) {
        this.main = main;

        String recipient = args[0];
        String message = args[1];




        for (Integer key : main.getClientManager().getConnections().keySet()){
            User User = main.getUserManager().getUser(main.getClientManager().getConnections().get(key).getIdentifier());
            if (User.getName().equals(recipient)){
                User me = main.getUserManager().getUser(sender.getIdentifier());
                me.setRecipient(main.getClientManager().getConnections().get(key));
                if (main.getChatRoomManager().getDmMap().isEmpty()){
                    main.getChatRoomManager().createDMRoom(main.getClientManager().getConnections().get(key), sender, roomName);
                    main.getChatRoomManager().addUserToDMRoom(sender, main.getClientManager().getConnections().get(key));
                    main.getClientManager().broadcastDM(message, sender, main.getClientManager().getConnections().get(key));
                    return;
                }
                if (!main.getChatRoomManager().getDmMap().containsKey(roomName)){
                    main.getChatRoomManager().createDMRoom(main.getClientManager().getConnections().get(key), sender, roomName);
                    main.getChatRoomManager().addUserToDMRoom(sender, main.getClientManager().getConnections().get(key));
                    main.getClientManager().broadcastDM(message, sender, main.getClientManager().getConnections().get(key));
                    dmRoomCount++;
                    return;
                }
                if (message.equals("join")){
                    main.getChatRoomManager().addUserToDMRoom(sender, main.getClientManager().getConnections().get(key));
                    return;
                }
                main.getClientManager().broadcastDM(message, sender, main.getClientManager().getConnections().get(key));
                return;
            }
        }

    }



    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
