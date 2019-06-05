package com.example.ps6waitingqueue;

import android.app.Application;
import android.content.Context;

import com.example.ps6waitingqueue.models.AppointmentList;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.models.UserList;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class App extends Application {
    private boolean firstTime = true;
    private AppointmentList appointments;
    private UserList users;
    private User currentUser;
    private MqttAndroidClient client;

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

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public void setAppointments(AppointmentList appointments) {
        this.appointments = appointments;
    }

    public void setUsers(UserList users) {
        this.users = users;
    }

    public MqttAndroidClient getClient() {
        return client;
    }

    public void setClient(MqttAndroidClient client) {
        this.client = client;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
