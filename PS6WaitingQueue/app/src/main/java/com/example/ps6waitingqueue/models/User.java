package com.example.ps6waitingqueue.models;

public class User {
    private String username;
    private String password;
    private String professor;
    private String destination;
    private String phoneNumber;
    private long id;

    public User(String username,String password, String professor, String destination, String phoneNumber, long id) {
        this.username = username;
        this.password = password;
        this.professor = professor;
        this.destination = destination;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }
    public User() {
        this.username = "";
        this.password = "";
        this.professor = "";
        this.destination = "";
        this.phoneNumber = "";
        this.id = 0;
    }

    public long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }
}
