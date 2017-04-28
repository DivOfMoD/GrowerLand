package com.divofmod.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

// Вызываем сервис
//  startService(new Intent(this, CheckWeather.class).putExtra());

public class CheckWeather extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //Все в отдельном потоке (т.к. по умолчанию UI)
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //  Передаем данные через Intent
                //  intent.getExtras().getString();
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
