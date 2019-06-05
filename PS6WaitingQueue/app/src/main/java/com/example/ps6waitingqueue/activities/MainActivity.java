package com.example.ps6waitingqueue.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.adapters.AppointmentsPagerAdapter;
import com.example.ps6waitingqueue.adapters.ListAppointmentsAdapter;
import com.example.ps6waitingqueue.listener.AppointmentsListener;
import com.example.ps6waitingqueue.listener.UsersListener;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.services.AppointmentsService;
import com.example.ps6waitingqueue.services.MqttService;
import com.example.ps6waitingqueue.services.UsersService;
import com.example.ps6waitingqueue.tasks.AppointmentsTask;

import java.util.ArrayList;
import java.util.Timer;

import static com.example.ps6waitingqueue.activities.LoginActivity.usersList;

public class MainActivity extends AppCompatActivity implements UsersListener, AppointmentsListener {

    public static ArrayList<Appointment> appointmentsList;
    public static User currentUser;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("username")) {
            String username = getIntent().getStringExtra("username");
            for (User user : usersList) {
                if (user.getUsername().equals(username)) {
                    currentUser = user;
                }
            }
        }
        MqttService mqttService = new MqttService(this);

        AppointmentsService.getAppointments(this, this);
        loadApppointment();
        checkPermission();

        ViewPager viewPager = findViewById(R.id.viewpagerappointments);
        AppointmentsPagerAdapter appointmentsPagerAdapter = new AppointmentsPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(appointmentsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs_appointments);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void loadApppointment() {
        Timer timer = new Timer();
        timer.schedule(new AppointmentsTask(this, this), 0, 2000);
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
        //setAppointmentsListView();

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
