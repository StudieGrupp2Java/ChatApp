package org.example.commandmanager;

import org.example.chatclient.ChatClient;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final List<Command> commands = new ArrayList<>();

    public CommandManager(ChatClient main){
        addCommand(new LeaveCommand(main));
        addCommand(new ConnectCommand(main));
        addCommand(new HelpCommand(main));
        addCommand(new ListEmojiCommand(main));
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public boolean executeCommand(String input){
        if (!input.startsWith("/")) {
            return false;
        }

        String[] commandArgs = input.split(" ");

        if (commandArgs.length == 0){
            System.out.println("Command cannot be empty!");
            return false;
        }
        for (Command command : commands){
            if (command.getName().equalsIgnoreCase(input)){
                command.execute(commandArgs);
                return true;
            }
            if (command.getName().equalsIgnoreCase(commandArgs[0])) {
                command.execute(commandArgs);
                return true;
            }
        }
        return false;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
