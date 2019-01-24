package com.diakonia.diakonapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MiFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();

        if(remoteMessage.getNotification()!=null){
            Log.d("prueba", "mensajeRecibidoDe: " +from +"\n Body: "+remoteMessage.getNotification().getBody());

            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        if(remoteMessage.getData().size()>0){

        }


    }

    public void showNotification(String title, String body){
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icono_diakonia_256px)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

    }
}
