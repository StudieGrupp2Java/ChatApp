package org.example.chatclient.commandmanager;

import org.example.chatclient.ChatClient;

public class ListEmojiCommand extends Command{
    public ListEmojiCommand(ChatClient main) {
        super("/emojis", main);
    }

    @Override
    public void execute(String[] args) {
        for (String key : main.getInputListener().getEmoji().getEmojiMap().keySet()){
            System.out.println(key + " = " + main.getInputListener().getEmoji().getEmojiMap().get(key));
        }
    }
}
