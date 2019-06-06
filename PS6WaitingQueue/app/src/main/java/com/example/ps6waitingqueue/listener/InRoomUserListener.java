package com.example.ps6waitingqueue.listener;

import com.example.ps6waitingqueue.models.User;

public interface InRoomUserListener {
    void inRoomCurrentUserUpdated(User user);
    void inRoomNextUserUpdated(User user);
    void inRoomUsersLeft(int i);

}
