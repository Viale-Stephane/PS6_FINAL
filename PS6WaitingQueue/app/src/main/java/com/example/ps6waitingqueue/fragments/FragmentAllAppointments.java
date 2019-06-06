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

import com.example.ps6waitingqueue.App;
import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.adapters.ListAppointmentsAdapter;
import com.example.ps6waitingqueue.listener.AppointmentListListener;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.User;

import java.util.ArrayList;

public class FragmentAllAppointments extends Fragment implements AppointmentListListener {
    private ListView listView;
    public ArrayList<Appointment> appointmentsList;
    public ArrayList<User> usersList;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1234;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_all_appointments, container, false);
        listView = view.findViewById(R.id.listViewAllAppointments);
        //setAppointmentsListView();
        appointmentsList = ((App) this.getActivity().getApplication()).getAppointments().getAppointments();
        Log.d("FragmentAll",appointmentsList.toString());
        usersList = ((App) this.getActivity().getApplication()).getUsers().getUsers();
        setAppointmentsListView();

        checkPermission();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        appointmentsList = ((App) this.getActivity().getApplication()).getAppointments().getAppointments();
        Log.d("FragmentAllResume",appointmentsList.toString());
        setAppointmentsListView();


    }

    public ArrayList<Appointment> filterTeacher(ArrayList<Appointment> appointments) {
        ArrayList<Appointment> toReturn = new ArrayList<>();
        for(Appointment appointment : appointments){
            if (appointment.getTeacherID() ==  ((App) this.getActivity().getApplication()).getCurrentUser().getId()) {
                toReturn.add(appointment);
            }
        }
        return toReturn;
    }

    public void setAppointmentsListView() {
        ListAppointmentsAdapter listAppointmentsAdapter = new ListAppointmentsAdapter(this.getContext(), filterTeacher(appointmentsList));
        listView.setAdapter(listAppointmentsAdapter);
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
    public void appointmentListUpdated(ArrayList<Appointment> appointments) {
        setAppointmentsListView();
    }
}
