package com.example.ps6waitingqueue.models;

import java.util.ArrayList;

public class AppointmentList {
    private ArrayList<Appointment> appointments;

    public AppointmentList() {
        this.appointments = new ArrayList<>();
    }

    public AppointmentList(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
}
