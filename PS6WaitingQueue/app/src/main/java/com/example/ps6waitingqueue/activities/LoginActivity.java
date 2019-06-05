package com.example.ps6waitingqueue.activities;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.listener.UsersListener;
import com.example.ps6waitingqueue.models.User;
import com.example.ps6waitingqueue.services.UsersService;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements UsersListener {

    Button loginButton;
    EditText usernameEditText;
    EditText passwordEditText;
    Button testConnectMqtt;
    public static ArrayList<User> usersList;

    private final String url = "tcp://127.0.0.1:1883";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsersService.getUsers(this, this);

        loginButton = findViewById(R.id.loginButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.password_input);
        testConnectMqtt = findViewById(R.id.testButtonConnect);
        testConnectMqtt.setOnClickListener(v-> {
            mqttConnect();
        });
    }

    public void mqttConnect(){
        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), url,
                        clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    String payload = "the payload";
                    byte[] encodedPayload = new byte[0];
                    try {
                        encodedPayload = payload.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        client.publish("currentAppointment",message);
                        Log.d("Mqtt", "onSuccess");
                    } catch (UnsupportedEncodingException | MqttException e){
                        System.err.println(e);
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("Mqtt", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

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
