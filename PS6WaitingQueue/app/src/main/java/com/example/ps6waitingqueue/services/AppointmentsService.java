package com.example.ps6waitingqueue.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ps6waitingqueue.listener.AppointmentsListener;
import com.example.ps6waitingqueue.models.Appointment;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

public class AppointmentsService {

    private static String urlAppointments = "http://127.0.0.1:1880/appointments";
    private static RequestQueue requestQueue;

    public static ArrayList<Appointment> getAppointments(Context context, AppointmentsListener listener) {
        requestQueue = Volley.newRequestQueue(context);
        ArrayList<Appointment> appointmentsList = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlAppointments, null, response -> {
            if (response.length() > 0) {
                Gson gson = new Gson();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Appointment appointment = gson.fromJson(response.getJSONObject(i).toString(), Appointment.class);
                        appointmentsList.add(appointment);
                    } catch (JSONException e) {
                        Log.e("VolleyError", e.getMessage());
                    }
                }
                listener.onRequestAppointmentsSuccess(appointmentsList);

            }
        }, error -> {
            Log.e("Volley", "error"+error.getMessage());
            listener.onRequestAppointmentsFailure(appointmentsList);
        });

        requestQueue.add(jsonArrayRequest);

        return appointmentsList;
    }

    public static void deleteAppointment(long id) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.DELETE, urlAppointments + "/" + id, null, response -> {
        }, error -> {

        });
        requestQueue.add(jsonArrayRequest);
    }
}


