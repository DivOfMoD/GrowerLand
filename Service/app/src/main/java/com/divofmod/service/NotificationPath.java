package com.divofmod.service;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotificationPath extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_path);
        findViewById(R.id.one).setBackground(getResources().getDrawable(R.drawable.one));
    }
}
