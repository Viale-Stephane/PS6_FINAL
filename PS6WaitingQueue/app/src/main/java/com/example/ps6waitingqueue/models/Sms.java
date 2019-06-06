package com.example.ps6waitingqueue.models;

public class Sms {

    private String tel;
    private String message;

    public Sms(String tel, String message) {
        this.tel = tel;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getTel() {
        return tel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
