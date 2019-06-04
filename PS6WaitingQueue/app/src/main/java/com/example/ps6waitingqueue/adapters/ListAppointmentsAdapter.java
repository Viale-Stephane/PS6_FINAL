package com.example.ps6waitingqueue.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ps6waitingqueue.activities.NextActivity;
import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.models.Appointment;

import java.util.ArrayList;

public class ListAppointmentsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Appointment> appointments;
    private Context context;
    public ListAppointmentsAdapter(Context context, ArrayList<Appointment> appointments){
        this.appointments = appointments;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return this.appointments.size();
    }

    @Override
    public Object getItem(int position) {
        return this.appointments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ConstraintLayout layout = (ConstraintLayout) mInflater.inflate(R.layout.appointment_list_layout,parent,false);
        TextView name =layout.findViewById(R.id.appointmentName);
        TextView date = layout.findViewById(R.id.textViewDate);
        TextView place = layout.findViewById(R.id.textViewPlace);
        TextView hour = layout.findViewById(R.id.textViewHour);
        name.setText(appointments.get(position).getTitle()+"");
        date.setText(parseDate(appointments.get(position).getDate())+"");
        place.setText(appointments.get(position).getStudentsID().size() + " / " + appointments.get(position).getPlaces()+" élèves");
        hour.setText(appointments.get(position).getStartTime() + " - " + appointments.get(position).getEndTime());
        name.setTag(position);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NextActivity.class);
                intent.putExtra("Appointment", appointments.get(position));
                context.startActivity(intent);
            }
        });
        return layout;
    }

    public String parseDate(String initDate){
        String[] parts = initDate.split("-");
        return (parts[2].substring(0,2)+"/"+parts[1]+"/"+parts[0]);
    }
}
