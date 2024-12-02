package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand(ChatClient main) {
        super("/help", main);
    }

    @Override
    public void execute(String[] args) {
        List<Command> commands = main.getCommandManager().getCommands();
        StringBuilder builder = new StringBuilder("Client-side commands: ");
        for (int i = 0 ; i < commands.size(); ++i) {
            builder.append(commands.get(i).getName());

            if (i != commands.size() - 1) {
                builder.append(", ");
            }
        }
        System.out.println(builder);
    }
}
