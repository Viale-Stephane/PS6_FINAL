package com.example.ps6waitingqueue.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ps6waitingqueue.App;
import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.models.AppointmentList;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.models.UserList;
import com.example.ps6waitingqueue.services.MQTTService;

import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText usernameEditText;
    EditText passwordEditText;
    private ArrayList<User> usersList;
    private MqttAndroidClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (((App) this.getApplication()).isFirstTime()) {
            ((App) this.getApplication()).setUsers(new UserList());
            ((App) this.getApplication()).setAppointments(new AppointmentList());
        }

        Intent intent = new Intent(this, MQTTService.class);
        startService(intent);

        usersList = ((App) this.getApplication()).getUsers().getUsers();

        loginButton = findViewById(R.id.loginButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.password_input);
    }


    public void onClickLoginButton(View v) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        for (User user : usersList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                Toast.makeText(this, "Vous êtes désormais connecté !", Toast.LENGTH_LONG).show();

                ((App) this.getApplication()).setCurrentUser(user);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(this, "Veuillez saisir un bon nom d'utilisateur/Mot de passe !", Toast.LENGTH_LONG).show();
    }
}
