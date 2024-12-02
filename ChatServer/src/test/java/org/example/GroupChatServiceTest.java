package org.example;

import org.example.chatrooms.ChatRoomManager;
import org.example.handling.ClientManager;
import org.example.handling.ConnectionHandler;
import org.example.users.User;
import org.example.users.UserManager;
import org.example.users.ChatRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GroupChatServiceTest {

    @Mock
    private ConnectionHandler user1;

    @Mock
    private ConnectionHandler user2;

    @Mock
    private ConnectionHandler user3;

    @Mock
    private ClientManager mockClientManager; // Mock ClientManager

    @Mock
    private ChatServer mockChatServer;  // Mock ChatServer

    @Mock
    private UserManager mockUserManager; // Mock UserManager

    @Mock
    private User mockUser; // Mock User

    @InjectMocks
    private ChatRoomManager chatRoomManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inject mockChatServer into ChatRoomManager so "main" is not null
        chatRoomManager = new ChatRoomManager(mockChatServer);

        // Configure mockChatServer to return mockUserManager and mockClientManager
        when(mockChatServer.getUserManager()).thenReturn(mockUserManager);
        when(mockChatServer.getClientManager()).thenReturn(mockClientManager);

        // Configure mockUserManager to return mockUser
        when(mockUserManager.getUser(anyInt())).thenReturn(mockUser);

        // Configure mockUser to have a valid role
        when(mockUser.getRole()).thenReturn(ChatRole.USER);  // Use USER as the role

        // Add mock users to a chat room
        chatRoomManager.createRoom("DefaultRoom", user1);
        chatRoomManager.addUserToRoom(user1, "DefaultRoom");
        chatRoomManager.addUserToRoom(user2, "DefaultRoom");
        chatRoomManager.addUserToRoom(user3, "DefaultRoom");
    }

    @Test
    public void testMessageSendingToAllGroupMembers() {
        // Arrange
        String message = "Hello Group!";

        // Act
        List<ConnectionHandler> usersInRoom = chatRoomManager.getUsersIn("DefaultRoom");
        for (ConnectionHandler user : usersInRoom) {
            user.sendMessage(message);
        }

        // Assert
        // Verify that each user in the room received the message
        verify(user1, times(1)).sendMessage(message);
        verify(user2, times(1)).sendMessage(message);
        verify(user3, times(1)).sendMessage(message);
    }

    @Test
    public void testMessageSavedInGroupHistory() {
        // Arrange
        String message = "Persist this message";

        // Act
        chatRoomManager.addToChatLog("DefaultRoom", message);

        // Assert
        // Verify that the chat log was updated
        assertTrue(chatRoomManager.getChatRoomLogs().get("DefaultRoom")
                .stream().anyMatch(log -> log.getMessage().equals(message)));
    }

    @Test
    public void testErrorHandlingForFailedMessage() {
        // Arrange
        String message = "Network error message";

        // Simulate a failure scenario for one user
        doThrow(new RuntimeException("Network Issue")).when(user2).sendMessage(message);

        // Act
        List<ConnectionHandler> usersInRoom = chatRoomManager.getUsersIn("DefaultRoom");
        for (ConnectionHandler user : usersInRoom) {
            try {
                user.sendMessage(message);
            } catch (RuntimeException e) {
                System.err.println("Error sending message to user: " + user.getIdentifier());
            }
        }

        // Assert
        verify(user1, times(1)).sendMessage(message);
        verify(user3, times(1)).sendMessage(message);
        verify(user2, times(1)).sendMessage(message); // Attempted but failed
    }
}
