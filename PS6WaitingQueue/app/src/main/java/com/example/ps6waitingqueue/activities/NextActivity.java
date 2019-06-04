package com.example.ps6waitingqueue.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.User;


public class NextActivity extends AppCompatActivity {

    private Button nextButton;
    private TextView nextName;
    private TextView currentName;
    private TextView nextTitle;
    private TextView numberStudentsLeft;
    private Appointment appointment;
    private final String noNewStudent = "Il n'y a pas de prochain étudiant.";
    private final String noStudent = "Aucun étudiant n'est dans votre bureau.";
    private final String currentStudent = "Etudiant actuel : ";
    private final String nextStudent = "Etudiant suivant : ";
    private final String numberLeft = "Nombre d'étudiants restants : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appointment = (Appointment) getIntent().getSerializableExtra("Appointment");
        final int[] studentNumber = {0};
        setContentView(R.layout.next_page);
        nextName = findViewById(R.id.nextName);
        if(appointment.getStudentsID().size() > 1)
            nextName.setText(nextStudent+getUserById(appointment.getStudentsID().get(0)).getUsername());
        currentName = findViewById(R.id.currentName);
        currentName.setText(noStudent);

        nextTitle = findViewById(R.id.nextTitle);
        nextTitle.setText(appointment.getTitle());

        numberStudentsLeft = findViewById(R.id.numberStudentNext);
        numberStudentsLeft.setText(numberLeft+appointment.getStudentsID().size());

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            if(studentNumber[0] < this.appointment.getStudentsID().size()) {
                nextButton.setBackgroundResource(R.drawable.circle_button_blue);
                nextButton.setText("Suivant");
                User user = getUserById(this.appointment.getStudentsID().get(studentNumber[0]));
                currentName.setText(currentStudent+user.getUsername());
                studentNumber[0]++;
                if(studentNumber[0] < this.appointment.getStudentsID().size()) {
                    nextName.setText(nextStudent+getUserById(this.appointment.getStudentsID().get(studentNumber[0])).getUsername());
                } else {
                    nextName.setText(noNewStudent);
                }

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED){
                    SmsManager.getDefault().sendTextMessage(user.getPhoneNumber(),null,"Mme.Pinna vous attend dans son bureau.",null,null);
                }

                int numberLeftOfStudent = appointment.getStudentsID().size() - studentNumber[0];
                numberStudentsLeft.setText(numberLeft + numberLeftOfStudent);
                if(numberLeftOfStudent == 0) {
                    nextButton.setText("Arrêter");
                    nextButton.setBackgroundResource(R.drawable.circle_button_red);
                }
            } else {
                onBackPressed();
                // quitter la page retour aux rdv & fermer le rdv
            }
        });

    }

    private User getUserById(long id) {
        for (User user : MainActivity.usersList) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}


