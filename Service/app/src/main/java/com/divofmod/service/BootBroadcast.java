package com.divofmod.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//BroadcastReceiver, чтобы сервис запускался  после перезагрузки устройства.
public class BootBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, CheckWeather.class));
    }
}
