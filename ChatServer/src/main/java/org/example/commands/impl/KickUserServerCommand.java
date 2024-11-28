package org.example.commands.impl;

import org.example.ChatServer;
import org.example.commands.ServerCommand;

public class KickUserServerCommand extends ServerCommand {

    @Override
    protected void execute(String[] args, ChatServer main) {

    }

    @Override
    protected int getExpectedArgsCount() {
        return 1;
    }
}
