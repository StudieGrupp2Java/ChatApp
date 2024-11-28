package org.example.util;

import org.example.ChatServer;
import org.example.users.User;

import java.util.concurrent.TimeUnit;

import static org.example.users.User.Status.AWAY;


public class UpdateTracker {

    private static final long AFK_THRESHOLD = TimeUnit.MINUTES.toMillis(1);
    private final ChatServer main;

    public UpdateTracker(ChatServer main) {
        this.main = main;
    }

    public void updateStatus(int identifier) {
        final User user = this.main.getUserManager().getUser(identifier);
        if (user == null) return;

        User.Status lastStatus = user.getStatus();
        user.setStatus(User.Status.ONLINE);

        if (lastStatus.equals(AWAY)) {
            main.getClientManager().broadcastMessageInRoom(Util.formatUserName(user) + " is no longer AFK.", true, user);
        }
    }

    private void setAway(User user) {
        user.setStatus(AWAY);

        main.getClientManager().broadcastMessageInRoom(Util.formatUserName(user) + " is now AFK.", true, user);

    }

    public void runTick() {
        new Thread(() -> {
            try {
                while (main.running) {
                    tick();
                    Thread.sleep(50); // tick 20 times a second
                }
            } catch (InterruptedException e) {
                System.err.println("Error in tickloop");
                e.printStackTrace();
            }

        }).start();
    }

    public void tick() {
        main.getUserManager().getUsers().stream()
                .filter(user -> user.getStatus().equals(User.Status.ONLINE))
                .forEach(user -> {
                    long diff = System.currentTimeMillis() - user.getLastSeen();

                    if (diff > AFK_THRESHOLD) {
                        this.setAway(user);
                    }
                });
    }
}
