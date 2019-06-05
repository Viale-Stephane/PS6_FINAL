package com.example.ps6waitingqueue.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.listener.UsersListener;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.services.UsersService;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements UsersListener {

    Button loginButton;
    EditText usernameEditText;
    EditText passwordEditText;
    public static ArrayList<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsersService.getUsers(this, this);

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
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(this, "Veuillez saisir un bon nom d'utilisateur/Mot de passe !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestUsersSuccess(ArrayList<User> users) {
        usersList = users;
    }

    @Override
    public void onRequestUsersFailure(ArrayList<User> users) {
        Toast.makeText(this, "Problème de connexion, veuillez vous connecter à internet !", Toast.LENGTH_LONG).show();
    }
}
