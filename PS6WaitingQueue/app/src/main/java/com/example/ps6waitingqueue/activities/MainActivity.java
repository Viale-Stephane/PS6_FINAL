package com.example.ps6waitingqueue.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ps6waitingqueue.App;
import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.adapters.AppointmentsPagerAdapter;
import com.example.ps6waitingqueue.models.User;

public class MainActivity extends AppCompatActivity {

    private User currentUser;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser = ((App) this.getApplication()).getCurrentUser();

        checkPermission();

        ViewPager viewPager = findViewById(R.id.viewpagerappointments);
        AppointmentsPagerAdapter appointmentsPagerAdapter = new AppointmentsPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(appointmentsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs_appointments);
        tabLayout.setupWithViewPager(viewPager);
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
