package com.example.ps6waitingqueue.tasks;

import android.content.Context;

import com.example.ps6waitingqueue.listener.AppointmentsListener;
import com.example.ps6waitingqueue.services.AppointmentsService;

import java.util.TimerTask;

public class AppointmentsTask extends TimerTask {

    private Context context;
    private AppointmentsListener listener;

    public AppointmentsTask(Context context, AppointmentsListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void run() {
        AppointmentsService.getAppointments(context, listener);
    }
}
