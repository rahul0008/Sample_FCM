package com.example.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

public class FireBaseService extends FirebaseMessagingService {

    private static final String TAG = "..fcm";

    @Override
    public void onNewToken(@NonNull @NotNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Notifcation : " + remoteMessage.getFrom());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel();
        }

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            getNotification(title, body);
        }
    }

    void getNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getApplicationContext(),"FCM")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        notificationManager.notify(1, notificationCompat.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    void  notificationChannel(){
        NotificationChannel notificationChannel = new NotificationChannel("FCM","NOTIFICATION",NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription("Demo FMC NOTIFICATION");

        NotificationManager notificationManager =(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

    }


}

