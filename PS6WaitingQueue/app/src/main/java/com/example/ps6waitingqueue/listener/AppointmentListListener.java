package com.example.ps6waitingqueue.listener;

import com.example.ps6waitingqueue.models.Appointment;

import java.util.ArrayList;

public interface AppointmentListListener {
    void appointmentListUpdated(ArrayList<Appointment> appointments);
}
