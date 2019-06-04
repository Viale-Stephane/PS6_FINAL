package com.example.ps6waitingqueue.listener;

import com.example.ps6waitingqueue.models.Appointment;

import java.util.ArrayList;

public interface AppointmentsListener {

    void onRequestAppointmentsSuccess(ArrayList<Appointment> appointments);
    void onRequestAppointmentsFailure(ArrayList<Appointment> appointments);

}
