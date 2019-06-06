package com.example.ps6waitingqueue.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.ps6waitingqueue.App;
import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.listener.InRoomUserListener;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.User;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;

public class NextActivity extends AppCompatActivity implements InRoomUserListener {

    private Button nextButton;
    private TextView nextName;
    private TextView currentName;
    private TextView nextTitle;
    private TextView numberStudentsLeft;
    private TextView timePerAppointment;
    private Appointment appointment;
    private final String noNewStudent = "Il n'y a pas de prochain étudiant.";
    private final String noStudent = "Aucun étudiant n'est dans votre bureau.";
    private final String currentStudent = "Etudiant actuel : ";
    private final String nextStudent = "Etudiant suivant : ";
    private final String numberLeft = "Nombre d'étudiants restants : ";
    private int timeSpent;
    private int usersLeft;
    User currentUser;
    User nextUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.appointment = (Appointment) getIntent().getSerializableExtra("Appointment");
        ((App) this.getApplication()).sendMessage("currentAppointment", String.valueOf(appointment.getId()));

        setContentView(R.layout.next_page);

        ((App) getApplication()).getMqttService().addUserRoomListener(this);


        currentUser = ((App)getApplication()).getInRoomCurrentUser();
        nextUser = ((App)getApplication()).getInRoomNextUser();
        usersLeft = ((App)getApplication()).getNumberOfStudentLeft();


        timePerAppointment = findViewById(R.id.timePerAppointment);
        nextName = findViewById(R.id.nextName);
        nextTitle = findViewById(R.id.nextTitle);
        numberStudentsLeft = findViewById(R.id.numberStudentNext);
        nextButton = findViewById(R.id.nextButton);
        currentName = findViewById(R.id.currentName);

        nextTitle.setText(appointment.getTitle());

        nextButton.setOnClickListener(v -> {
            ((App)this.getApplication()).sendMessage("nextUser","next");
            nextButton.setBackgroundResource(R.drawable.circle_button_blue);
            nextButton.setText("Suivant");
            /*
            if(currentUser!=null) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    SmsManager.getDefault().sendTextMessage(currentUser.getPhoneNumber(), null, "Mme.Pinna vous attend dans son bureau.", null, null);
                }
            }
            */
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((App)this.getApplication()).sendMessage("backToList","back");
    }

    @Override
    public void inRoomCurrentUserUpdated(User user) {
        Log.d("boutonText",String.valueOf(nextButton.getText()));
        if(nextButton.getText().toString().equals("Commencer")){
            Log.d("boutonText","Je suis dedans");
            currentName.setText(noStudent);
            nextName.setText(nextStudent + user.getUsername());
            numberStudentsLeft.setText(numberLeft + appointment.getStudentsID().size());
        }else {
            this.currentUser = user;
            this.currentName.setText(currentStudent + user.getUsername());
        }

    }

    @Override
    public void inRoomNextUserUpdated(User user) {
        if(nextButton.getText().toString().equals("Commencer")){

        }else {
            this.nextUser = user;
            Log.d("next", user.getUsername());
            if (user.getUsername().equals("nobody")) {
                this.nextName.setText(noNewStudent);
            } else {
                this.nextName.setText(nextStudent + user.getUsername());
            }
        }

    }

    @Override
    public void inRoomUsersLeft(int i) {
        Log.d("compteur", String.valueOf(i));
        this.nextButton.setBackgroundResource(R.drawable.circle_button_blue);
        this.nextButton.setText("Suivant");
        if(i == 0){
            this.numberStudentsLeft.setText("");
            this.nextButton.setBackgroundResource(R.drawable.circle_button_red);
            this.nextButton.setText("Arrêter");
        }else if( i < 0){
            ((App)getApplication()).sendMessage("deleteAppointment",String.valueOf(appointment.getId()));
            onBackPressed();
        }
        else {
            this.usersLeft = i;
            this.numberStudentsLeft.setText(numberLeft + i);
        }

    }

    @Override
    public void inRoomTimeLeft(int i) {
        Log.d("tomeleft", String.valueOf(i));
        timePerAppointment.setText("Temps restant :" + LocalTime.MIN.plus(Duration.ofMinutes(i)).toString());
    }
}


