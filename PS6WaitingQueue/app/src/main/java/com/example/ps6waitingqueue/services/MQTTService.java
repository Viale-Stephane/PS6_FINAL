package com.example.ps6waitingqueue.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.example.ps6waitingqueue.App;
import com.example.ps6waitingqueue.models.AppointmentList;
import com.example.ps6waitingqueue.models.UserList;
import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MqttService extends IntentService {

    public MqttService() {
        super("MqttService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://127.0.0.1:1883",
                        clientId);

        ((App) getApplication()).setClient(client);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("MQTT", "onSuccess");
                    if (client.isConnected()) {
                        subscribeUsers(client);
                        subscribeAppointments(client);
                        sendMessage(client, "needUsers", "request");
                        sendMessage(client, "needAppointments", "request");
                        Log.d("MQTT-Client", "Good");
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MQTT", "onFailure");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (topic.equals("users")) {
                    Log.d("MQTT-users", new String(message.getPayload()));
                    Log.d("MQTT-users", "ça passe sur user");

                    String response = new String(message.getPayload());
                    Gson gson = new Gson();
                    UserList userList = gson.fromJson(response, UserList.class);
                    ((App) getApplication()).getUsers().getUsers().clear();
                    ((App) getApplication()).getUsers().getUsers().addAll(userList.getUsers());
                } else if (topic.equals("appointments")) {
                    Log.d("MQTT-appointments", new String(message.getPayload()));
                    Log.d("MQTT-appointments", "ça passe sur appointment");

                    String response = new String(message.getPayload());
                    Gson gson = new Gson();
                    AppointmentList appointmentList = gson.fromJson(response, AppointmentList.class);
                    ((App) getApplication()).getAppointments().getAppointments().clear();
                    ((App) getApplication()).getAppointments().getAppointments().addAll(appointmentList.getAppointments());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    private void sendMessage(MqttAndroidClient client, String topic, String payload) {
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribeUsers(MqttAndroidClient client) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe("users", qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Log.d("MQTT-Subscribe", "Users Good");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    private void subscribeAppointments(MqttAndroidClient client) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe("appointments", qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Log.d("MQTT-Subscribe", "Appointments Good");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
