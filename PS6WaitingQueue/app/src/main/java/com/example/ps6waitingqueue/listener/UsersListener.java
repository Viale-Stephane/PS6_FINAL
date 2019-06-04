package com.example.ps6waitingqueue.listener;

import com.example.ps6waitingqueue.models.User;

import java.util.ArrayList;

public interface UsersListener {

    void onRequestUsersSuccess(ArrayList<User> users);
    void onRequestUsersFailure(ArrayList<User> users);

}
