package com.example.ps6waitingqueue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ps6waitingqueue.adapters.ListAppointmentsAdapter;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    public static String urlAppointments = "http://127.0.0.1:9428/api/appointments";
    public static String urlUsers = "http://127.0.0.1:9428/api/login";
    //public static String url = "https://pastebin.com/raw/i78UJB2F";
    RequestQueue requestQueue;
    static ArrayList<Appointment> appointmentsList;
    static ArrayList<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listViewAppointments);
        requestQueue = Volley.newRequestQueue(this);
        appointmentsList = new ArrayList<>();
        usersList = new ArrayList<>();
        getAppointments();
        getUsers();
    }
    public void setListView(){
        ListAppointmentsAdapter listAppointmentsAdapter = new ListAppointmentsAdapter(MainActivity.this,appointmentsList);
        listView.setAdapter(listAppointmentsAdapter);
    }

    public void getAppointments(){
        Log.d("getAppointments","je suis là !");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,urlAppointments, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("getAppointments", response.toString());
                if (response.length() > 0) {
                    Gson gson = new Gson();
                    Log.d("getAppointments","je suis > 0 !");
                    for (int i = 0; i < response.length(); i++) {
                        Appointment appointment = null;
                        try {
                            appointment = gson.fromJson(response.getJSONObject(i).toString(), Appointment.class);
                            appointmentsList.add(appointment);
                        } catch (JSONException e) {
                            Log.e("VolleyError",e.getMessage());
                        }
                    }
                    Log.d("Taille",String.valueOf(appointmentsList.size()));
                    setListView();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley",error.getMessage());
            }
        });
        this.requestQueue.add(jsonArrayRequest);
    }

    public void getUsers(){
        Log.d("getAppointments","je suis là !");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,urlUsers, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("getAppointments", response.toString());
                if (response.length() > 0) {
                    Gson gson = new Gson();
                    Log.d("getAppointments","je suis > 0 !");
                    for (int i = 0; i < response.length(); i++) {
                        User user = null;
                        try {
                            user = gson.fromJson(response.getJSONObject(i).toString(), User.class);
                            usersList.add(user);
                        } catch (JSONException e) {
                            Log.e("VolleyError",e.getMessage());
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Volley",error.getMessage());
            }
        });
        this.requestQueue.add(jsonArrayRequest);

    }

}
