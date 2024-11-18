package org.example.commands;

public class LoginCommand extends Command {

    @Override
    protected void execute(String[] args) {
        String username = args[0];
        String password = args[1];

        // Authentication logic
        System.out.println("Logging in user: " + username);
    }

    @Override
    protected int getExpectedArgsCount() {
        return 2;
    }
}
