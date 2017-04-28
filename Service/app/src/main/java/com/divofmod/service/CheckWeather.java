package com.divofmod.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



public class CheckWeather extends Service {

    NotificationManager manager;


    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    //Все в отдельном потоке (т.к. по умолчанию UI)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

                notifyUser(NotificationPath.class,"GrowerLand","Уведомление GrowerLand",R.mipmap.notification);

        return super.onStartCommand(intent, Service.START_FLAG_RETRY, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void notifyUser(final Class classForIntent, final String contentTitle, final String contentText, final int ico) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                PendingIntent pendingIntent = PendingIntent.getActivity(CheckWeather.this, 1, new Intent(CheckWeather.this, classForIntent), 0);

                Notification.Builder builder = new Notification.Builder(CheckWeather.this);

                builder.setAutoCancel(true);
                builder.setContentTitle(contentTitle); //Заголовок
                builder.setContentText(contentText); //Основной текст
                builder.setSmallIcon(ico);
                builder.setContentIntent(pendingIntent);
                builder.setOngoing(true);
                builder.build();
                manager.notify(11, builder.getNotification());
            }
        }).start();
    }
}
