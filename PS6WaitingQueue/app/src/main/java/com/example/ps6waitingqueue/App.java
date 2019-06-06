package com.example.ps6waitingqueue;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.ps6waitingqueue.models.AppointmentList;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.models.UserList;
import com.example.ps6waitingqueue.services.MqttService;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class App extends Application {
    private boolean firstTime = true;
    private AppointmentList appointments;
    private UserList users;
    private User currentUser;
    private User inRoomCurrentUser;
    private User inRoomNextUser;
    private MqttService mqttService;
    private MqttAndroidClient client;
    private int numberOfStudentLeft;

    public int getNumberOfStudentLeft(){
        return numberOfStudentLeft;
    }

    public void setNumberOfStudentLeft(int n){
        this.numberOfStudentLeft = n;
    }

    public User getInRoomCurrentUser(){
        return inRoomCurrentUser;
    }
    public void setInRoomCurrentUser(User user){
        inRoomCurrentUser = user;
    }
    public User getInRoomNextUser(){
        return inRoomNextUser;
    }
    public void setInRoomNextUser(User user){
        inRoomCurrentUser = user;
    }

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

    public MqttService getMqttService() {
        return mqttService;
    }

    public void setMqttService(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    public void sendMessage(String topic, String message) {
        this.mqttService.sendMessage(this.client, topic, message);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
