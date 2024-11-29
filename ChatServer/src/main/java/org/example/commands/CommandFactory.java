package org.example.commands;

import org.example.commands.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    private static final Map<String, Supplier<ServerCommand>> serverCommandMap = new HashMap<>();

    static {
        commandMap.put("login", LoginCommand::new);
        commandMap.put("register", RegisterCommand::new);
        commandMap.put("dm", DMCommand::new);
        commandMap.put("changepassword", ChangePasswordCommand::new);
        commandMap.put("logout", LogoutCommand::new);
        commandMap.put("removeword", RemoveBannedWordCommand::new);
        commandMap.put("addword", AddBannedWordsCommand::new);
        commandMap.put("help", HelpCommand::new);
        commandMap.put("deleteaccount", DeleteAccountCommand::new);
        commandMap.put("confirmdelete", ConfirmDeleteCommand::new);
        commandMap.put("online", OnlineCommand::new);
        commandMap.put("block", BlockUserCommand::new);
        commandMap.put("unblock", RemoveBlockedUserCommand::new);
        commandMap.put("listblocked", ListBlockedUsersCommand::new);
        commandMap.put("create", CreateRoomCommand::new);
        commandMap.put("leaveroom", LeaveRoomCommand::new);
        commandMap.put("join", JoinRoomCommand::new);
        commandMap.put("rooms", ListActiveRoomsCommand::new);
        commandMap.put("dmsound", ToggleDMSoundCommand::new);
        commandMap.put("messagesound", ToggleMessageSoundCommand::new);
        commandMap.put("kick", KickUserCommand::new);

        serverCommandMap.put("admin", CreateAdminServerCommand::new);
        serverCommandMap.put("kick", KickUserServerCommand::new);
    }

    public static Command getCommand(String commandName) {
        Supplier<Command> commandSupplier = commandMap.get(commandName.toLowerCase());
        if (commandSupplier != null) {
            return commandSupplier.get();
        }
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }

    public static ServerCommand getServerCommand(String commandName) {
        Supplier<ServerCommand> commandSupplier = serverCommandMap.get(commandName.toLowerCase());
        if (commandSupplier != null) {
            return commandSupplier.get();
        }
        throw new IllegalArgumentException("Unknown command: " + commandName);
    }

    public static Map<String, Supplier<Command>> getCommandMap(){
        return commandMap;
    }
}