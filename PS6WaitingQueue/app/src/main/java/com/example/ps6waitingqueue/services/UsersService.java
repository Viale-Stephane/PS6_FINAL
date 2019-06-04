package com.example.ps6waitingqueue.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ps6waitingqueue.listener.AppointmentsListener;
import com.example.ps6waitingqueue.listener.UsersListener;
import com.example.ps6waitingqueue.models.User;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

public class UsersService {

    private static String urlUsers = "http://127.0.0.1:1880/users";
    private static RequestQueue requestQueue;

    public static ArrayList<User> getUsers(Context context, UsersListener listener) {
        requestQueue = Volley.newRequestQueue(context);
        ArrayList<User> usersList = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,urlUsers, null, response -> {
            if (response.length() > 0) {
                Gson gson = new Gson();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        User user = gson.fromJson(response.getJSONObject(i).toString(), User.class);
                        usersList.add(user);
                    } catch (JSONException e) {
                        Log.e("VolleyError", e.getMessage());
                    }
                }
                listener.onRequestUsersSuccess(usersList);
            }
        }, error -> {
            Log.e("Volley", "error"+error.getMessage());
            listener.onRequestUsersFailure(usersList);
        });

        requestQueue.add(jsonArrayRequest);

        return usersList;
    }

}
