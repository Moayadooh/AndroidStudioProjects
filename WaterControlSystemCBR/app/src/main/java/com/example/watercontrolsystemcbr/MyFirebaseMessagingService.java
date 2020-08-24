package com.example.watercontrolsystemcbr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                makeNotificationChannel("CHANNEL_1", "Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "CHANNEL_1");
            notification
                    .setSmallIcon(R.drawable.plant)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.green))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setNumber(0);
            NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.notify(NotificationID.getID(), notification.build());
        }
    }

    //Notification channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeNotificationChannel(String id, String name, int importance)
    {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

}
