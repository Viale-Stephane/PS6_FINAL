package com.example.ps6waitingqueue.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Appointment implements Serializable {
    private long id;
    private String title;
    private String desc;
    private String startTime;
    private String endTime;
    private String date;
    private int places;
    private long teacherID;
    private ArrayList<Long> studentsID;
    public Appointment(int id, String title, String desc, String startTime, String endTime, String date,
                       int places, int teacherID){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.places = places;
        this.teacherID = teacherID;
    }

    public Appointment(){
        this.id = 0;
        this.title = "";
        this.desc = "";
        this.startTime = "";
        this.endTime = "";
        this.date = "";
        this.places = 0;
        this.teacherID = 0;
    }

    public ArrayList<Long> getStudentsID() {
        return studentsID;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
    }

    public int getPlaces() {
        return places;
    }

    public long getTeacherID() {
        return teacherID;
    }
}

