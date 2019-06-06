package com.example.ps6waitingqueue.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentTodayAppointments extends Fragment implements AppointmentListListener {
    private ListView listView;
    private ArrayList<Appointment> appointmentsList;
    private ArrayList<User> usersList;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1234;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_today_appointments, container, false);
        listView = view.findViewById(R.id.listViewTodayAppointments);
        //setAppointmentsListView();
        appointmentsList = ((App) this.getActivity().getApplication()).getAppointments().getAppointments();
        usersList = ((App) this.getActivity().getApplication()).getUsers().getUsers();
        ((App) this.getActivity().getApplication()).getMqttService().addAppointmentListListener(this);
        setAppointmentsListView();
        checkPermission();
        return view;
    }

    public ArrayList<Appointment> filterTeacher(ArrayList<Appointment> appointments) {
        ArrayList<Appointment> toReturn = new ArrayList<>();
        for(Appointment appointment : appointments){
            if (appointment.getTeacherID() == ((App) this.getActivity().getApplication()).getCurrentUser().getId()) {
                toReturn.add(appointment);
            }
        }
        return toReturn;
    }

    public ArrayList<Appointment> filterToday(ArrayList<Appointment> appointments){
        ArrayList<Appointment> toReturn = new ArrayList<>();
        DateFormat dateFormatDay = new SimpleDateFormat("dd");
        DateFormat dateFormatMonth = new SimpleDateFormat("MM");
        Date date = new Date();
        String todayDateDay = dateFormatDay.format(date);
        String todayDateMonth = dateFormatMonth.format(date);
        for(Appointment temp : appointments){
            if (checkParseDate(temp.getDate(),todayDateDay,todayDateMonth)) {
                toReturn.add(temp);
            }
        }
        return toReturn;

    }

    public void setAppointmentsListView() {
        ListAppointmentsAdapter listAppointmentsAdapter = new ListAppointmentsAdapter(this.getContext(), filterTeacher(filterToday(appointmentsList)));
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

    public boolean checkParseDate(String initDate, String todayDay,String todayMonth){
        String[] parts = initDate.split("-");
        return ((parts[2].substring(0,2)).equals(todayDay) && parts[1].equals(todayMonth));
    }

    @Override
    public void appointmentListUpdated(ArrayList<Appointment> appointments) {
        appointmentsList = appointments;
        setAppointmentsListView();
    }
}
