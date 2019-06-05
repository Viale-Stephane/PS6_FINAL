package com.example.ps6waitingqueue.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MqttService {
    private final String url = "tcp://127.0.0.1:1883";
    public static MqttAndroidClient client;

    public MqttService(Context context) {
        mqttConnect(context);
    }

    public void sendStringMessage(String topic, String message){
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = message.getBytes("UTF-8");
            MqttMessage mqttMessage = new MqttMessage(encodedPayload);
            client.publish(topic,mqttMessage);
        } catch (UnsupportedEncodingException | MqttException e){
            System.err.println(e);
        }


    }
    public void sendGsonObject(String topic, Object object){
        byte[] encodedPayload = new byte[0];
        Gson gson = new Gson();
        String message = gson.toJson(object);
        try {
            encodedPayload = message.getBytes("UTF-8");
            MqttMessage mqttMessage = new MqttMessage(encodedPayload);
            client.publish(topic,mqttMessage);
        } catch (UnsupportedEncodingException | MqttException e){
            System.err.println(e);
        }

    }

    public void mqttConnect(Context context){
        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(context, url,
                        clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("Mqtt", "onSuccess");
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
}
