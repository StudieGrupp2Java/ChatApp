package org.example.chatrooms;

import lombok.Getter;

@Getter
public class DM {
    String recipient;
    String sender;
    String message;

    public DM(String message, String sender, String recipient){
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }
}
