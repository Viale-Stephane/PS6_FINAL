package com.example.ps6waitingqueue.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.adapters.ListAppointmentsAdapter;
import com.example.ps6waitingqueue.listener.AppointmentsListener;
import com.example.ps6waitingqueue.listener.UsersListener;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.services.AppointmentsService;
import com.example.ps6waitingqueue.services.UsersService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UsersListener, AppointmentsListener {

    private ListView listView;
    static ArrayList<Appointment> appointmentsList;
    static ArrayList<User> usersList;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listViewAppointments);
        AppointmentsService.getAppointments(this, this);
        UsersService.getUsers(this, this);
        checkPermission();
    }

    public void setAppointmentsListView() {
        Log.d("appointments", appointmentsList.get(0).toString());
        ListAppointmentsAdapter listAppointmentsAdapter = new ListAppointmentsAdapter(MainActivity.this,appointmentsList);
        listView.setAdapter(listAppointmentsAdapter);
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

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }
}
