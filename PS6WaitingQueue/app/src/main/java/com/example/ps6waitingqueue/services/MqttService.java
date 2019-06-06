package com.example.ps6waitingqueue.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.example.ps6waitingqueue.App;
import com.example.ps6waitingqueue.listener.AppointmentListListener;
import com.example.ps6waitingqueue.listener.InRoomUserListener;
import com.example.ps6waitingqueue.models.Appointment;
import com.example.ps6waitingqueue.models.AppointmentList;
import com.example.ps6waitingqueue.models.User;
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
import org.json.JSONArray;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MqttService extends IntentService {

    private ArrayList<AppointmentListListener> appointmentListListeners = new ArrayList<>();
    private ArrayList<InRoomUserListener> inRoomUserListeners = new ArrayList<>();

    public MqttService() {
        super("MqttService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ((App) this.getApplication()).setMqttService(this);


        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://127.0.0.1:1883", //adresse broker local
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
                        subscribeInRoomCurrentUser(client);
                        subscribeInRoomNextUser(client);
                        subscribeUserLeft(client);
                        sendMessage(client, "newConnection", "I'm connected");
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
            public void messageArrived(String topic, MqttMessage message) {
                Gson gson = new Gson();
                if (topic.equals("users")) {
                    String response = new String(message.getPayload());
                    User[] userList = gson.fromJson(response, User[].class);

                    ArrayList<User> userArrayList = new ArrayList<>(Arrays.asList(userList));

                    ((App) getApplication()).getUsers().getUsers().clear();
                    ((App) getApplication()).getUsers().getUsers().addAll(userArrayList);
                } else if (topic.equals("appointments")) {
                    String response = new String(message.getPayload());
                    Appointment[] appointmentsList = gson.fromJson(response, Appointment[].class);

                    ArrayList<Appointment> appointmentArrayList = new ArrayList<>(Arrays.asList(appointmentsList));

                    ((App) getApplication()).getAppointments().getAppointments().clear();
                    ((App) getApplication()).getAppointments().getAppointments().addAll(appointmentArrayList);
                    fireNewAppointmentList(appointmentArrayList);

                } else if (topic.equals("inRoomCurrentUser")) {
                    Log.d("inRoomCurrentUser", "new msg in topic");
                    String response = new String(message.getPayload());
                    Log.d("inRoomCurrentUser",response);

                    User inRoomCurrentUser = gson.fromJson(response, User.class);

                    Log.d("inRoomCurrentUser",inRoomCurrentUser.getUsername());
                    ((App)getApplication()).setInRoomCurrentUser(inRoomCurrentUser);
                    fireNewInRoomCurrentUser(inRoomCurrentUser);

                } else if (topic.equals("inRoomNextUser")) {
                    Log.d("inRoomNextUser", "new msg in topic");
                    String response = new String(message.getPayload());
                    Log.d("inRoomNextUser",response);

                    User inRoomNextUser = gson.fromJson(response, User.class);

                    Log.d("User",inRoomNextUser.getUsername());
                    ((App)getApplication()).setInRoomCurrentUser(inRoomNextUser);
                    fireNewInRoomNextUser(inRoomNextUser);
                } else if (topic.equals("userLeft")) {
                    Log.d("inRoomNextUser", "new msg in topic");
                    String response = new String(message.getPayload());
                    Log.d("inRoomNextUser",response);

                    int usersLeft = gson.fromJson(response, Integer.class);

                    ((App)getApplication()).setNumberOfStudentLeft(usersLeft);
                    fireUserLeftInRoom(usersLeft);
                } else if (topic.equals("inRoomTimeLeft")) {
                    Log.d("inRoomTimeLeft", "new msg in topic");
                    String response = new String(message.getPayload());
                    Log.d("inRoomTimeLeft",response);

                    int usersLeft = gson.fromJson(response, Integer.class);

                    ((App)getApplication()).setNumberOfStudentLeft(usersLeft);
                    fireUserLeftInRoom(usersLeft);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public void sendMessage(MqttAndroidClient client, String topic, String payload) {
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
    private void subscribeUserLeft(MqttAndroidClient client) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe("userLeft", qos);
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
    private void subscribeInRoomCurrentUser(MqttAndroidClient client) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe("inRoomCurrentUser", qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Log.d("MQTT-Subscribe", "inRoomCurrentUser Good");
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
    private void subscribeInRoomNextUser(MqttAndroidClient client) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe("inRoomNextUser", qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Log.d("MQTT-Subscribe", "inRoomNextUser Good");
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

    private void subscribeInRoomTimeLeft(MqttAndroidClient client) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe("inRoomTimeLeft", qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Log.d("MQTT-Subscribe", "inRoomTimeLeft Good");
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

    public void addAppointmentListListener(AppointmentListListener appointmentListListener) {
        this.appointmentListListeners.add(appointmentListListener);
    }

    public void addUserRoomListener(InRoomUserListener inRoomUserListener) {
        this.inRoomUserListeners.add(inRoomUserListener);
    }

    private void fireNewAppointmentList(ArrayList<Appointment> appointments) {
        for (AppointmentListListener appointmentListListener : this.appointmentListListeners) {
            appointmentListListener.appointmentListUpdated(appointments);
        }
    }
    private void fireNewInRoomCurrentUser(User inRoomCurrentUser) {
        for (InRoomUserListener inRoomUserListener : this.inRoomUserListeners) {
            inRoomUserListener.inRoomCurrentUserUpdated(inRoomCurrentUser);
        }
    }
    private void fireNewInRoomNextUser(User inRoomNextUser) {
        for (InRoomUserListener inRoomUserListener : this.inRoomUserListeners) {
            inRoomUserListener.inRoomNextUserUpdated(inRoomNextUser);
        }
    }
    private void fireUserLeftInRoom(int left) {
        for (InRoomUserListener inRoomUserListener : this.inRoomUserListeners) {
            inRoomUserListener.inRoomUsersLeft(left);
        }
    }
    private void firTimeLeftInRoom(int timeleft) {
        for (InRoomUserListener inRoomUserListener : this.inRoomUserListeners) {
            inRoomUserListener.inRoomTimeLeft(timeleft);
        }
    }
}
