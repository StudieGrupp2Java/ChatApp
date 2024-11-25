package org.example.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    static {
        commandMap.put("login", LoginCommand::new);
        commandMap.put("register", RegisterCommand::new);
        commandMap.put("dm", DMCommand::new);
        commandMap.put("changepassword", ChangePasswordCommand::new);
        commandMap.put("logout", LogoutCommand::new);
        commandMap.put("removeword", RemoveBannedWordCommand::new);
        commandMap.put("addword", AddBannedWordsCommand::new);
        commandMap.put("help", HelpCommand::new);
        commandMap.put("online", OnlineCommand::new);
        commandMap.put("block", BlockUserCommand::new);
        commandMap.put("unblock", RemoveBlockedUserCommand::new);
        commandMap.put("listblocked", ListBlockedUsersCommand::new);
        commandMap.put("createroom", CreateRoomCommand::new);
        commandMap.put("leaveroom", LeaveRoomCommand::new);
        commandMap.put("join", JoinRoomCommand::new);
        commandMap.put("listrooms", ListActiveRoomsCommand::new);
    }

    public static Command getCommand(String commandName) {
        Supplier<Command> commandSupplier = commandMap.get(commandName.toLowerCase());
        if (commandSupplier != null) {
            return commandSupplier.get();
        }
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }

    public static Map<String, Supplier<Command>> getCommandMap(){
        return commandMap;
    }
}