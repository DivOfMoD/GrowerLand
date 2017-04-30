package com.example.nick.growerland;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public abstract class NotifyUser {

    public static void notifyUser(Context context, Class classForIntent, String contentTitle, String contentText, int icoId) {

        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(context, classForIntent), 0);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(true);
        builder.setContentTitle(contentTitle); //Заголовок
        builder.setContentText(contentText); //Основной текст
        builder.setSmallIcon(icoId);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.build();
        manager.notify(11, builder.getNotification());

    }

}
