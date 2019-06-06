package com.example.ps6waitingqueue.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ps6waitingqueue.App;
import com.example.ps6waitingqueue.R;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.User;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class NextActivity extends AppCompatActivity {

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
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.appointment = (Appointment) getIntent().getSerializableExtra("Appointment");
        timeSpent = 0;
        this.thread = new Thread();
        setContentView(R.layout.next_page);
        timePerAppointment = findViewById(R.id.timePerAppointment);
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int numberOfMinuteByAppointment = getTimeByAppointment(appointment.getStartTime(), appointment.getEndTime(), appointment.getStudentsID().size());
        final int[] studentNumber = {0};
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
                this.timeSpent = 0;
                if(thread.isAlive()) {
                    this.thread.interrupt();
                }
                startTimer(numberOfMinuteByAppointment);
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
                String payload = String.valueOf(appointment.getId());
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    ((App) this.getApplication()).getClient().publish("deleteAppointment", message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }

                // TODO : AppointmentsService.deleteAppointment(appointment.getId());
                //onBackPressed();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                // quitter la page retour aux rdv & fermer le rdv
            }
        });

    }

    private void startTimer(int numberOfMinuteByAppointment){
        updateTimePerAppointment(numberOfMinuteByAppointment, timeSpent);
        this.thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        //Thread.sleep(60000); //minutes
                        Thread.sleep(1000); //secondes
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeSpent ++;
                                updateTimePerAppointment(numberOfMinuteByAppointment, timeSpent);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }

    private void updateTimePerAppointment(int numberOfMinuteByAppointment, int timeSpent){
        if(numberOfMinuteByAppointment == -1){
            timePerAppointment.setText("");
        } else {
            if(this.timeSpent >= numberOfMinuteByAppointment){
                timePerAppointment.setText("Vous devriez changer d'élève pour être à l'heure");
            } else {
                timePerAppointment.setText("Il vous reste " + (numberOfMinuteByAppointment - timeSpent) + " minutes avec cet élève");
            }//timePerAppointment.setText("coucou");
        }
    }

    private User getUserById(long id) {
        for (User user : ((App) this.getApplication()).getUsers().getUsers()) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    private int getTimeByAppointment(String startTime, String endTime, int numberOfAppointment){
        int totalMinute = 0;
        String[] parts = startTime.split(":");
        int startHour = Integer.valueOf(parts[0]);
        int startMinute = Integer.valueOf(parts[1]);
        String[] partsBis = endTime.split(":");
        int endHour = Integer.valueOf(partsBis[0]);
        int endMinute = Integer.valueOf(partsBis[1]);
        int minuteTotalStart = startHour * 60 + startMinute;
        int minuteTotalEnd = endHour * 60 + endMinute;
        totalMinute = minuteTotalEnd - minuteTotalStart;
        if(numberOfAppointment == 0){
            return -1;
        }
        return (totalMinute/numberOfAppointment);
    }
}


