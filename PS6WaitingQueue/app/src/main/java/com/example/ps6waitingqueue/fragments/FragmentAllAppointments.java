package com.example.ps6waitingqueue.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.activities.MainActivity;
import com.example.ps6waitingqueue.adapters.ListAppointmentsAdapter;
import com.example.ps6waitingqueue.listener.AppointmentsListener;
import com.example.ps6waitingqueue.listener.UsersListener;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.services.AppointmentsService;
import com.example.ps6waitingqueue.services.UsersService;
import com.example.ps6waitingqueue.tasks.AppointmentsTask;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;

public class FragmentAllAppointments extends Fragment implements UsersListener, AppointmentsListener {
    private ListView listView;
    public ArrayList<Appointment> appointmentsList;
    public ArrayList<User> usersList;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1234;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_all_appointments, container, false);
        listView = view.findViewById(R.id.listViewAllAppointments);
        //setAppointmentsListView();
        AppointmentsService.getAppointments(this.getContext(), this);
        loadApppointment();
        UsersService.getUsers(this.getContext(), this);
        checkPermission();
        return view;
    }

    public void setAppointmentsListView() {
        ListAppointmentsAdapter listAppointmentsAdapter = new ListAppointmentsAdapter(this.getContext(),appointmentsList);
        listView.setAdapter(listAppointmentsAdapter);
    }

    public void loadApppointment() {
        Timer timer = new Timer();
        timer.schedule(new AppointmentsTask(this.getContext(), this), 0, 2000);
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestUsersSuccess(ArrayList<User> users) {
        usersList = users;
    }

    @Override
    public void onRequestUsersFailure(ArrayList<User> users) {
        usersList = new ArrayList<>();
    }

    @Override
    public void onRequestAppointmentsSuccess(ArrayList<Appointment> appointments) {
        appointmentsList = appointments;
        setAppointmentsListView();
    }

    @Override
    public void onRequestAppointmentsFailure(ArrayList<Appointment> appointments) {
        appointmentsList = new ArrayList<>();
    }
}
