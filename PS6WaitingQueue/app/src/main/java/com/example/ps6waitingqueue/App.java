package com.example.ps6waitingqueue;

import android.app.Application;
import android.content.Context;

import com.example.ps6waitingqueue.models.AppointmentList;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.models.UserList;

public class App extends Application {
    private AppointmentList appointments;
    private UserList users;
    private User currentUser;

    public UserList getUsers() {
        return users;
    }

    public AppointmentList getAppointments() {
        return appointments;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
