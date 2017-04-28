package com.example.nick.growerland.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.nick.growerland.APIProvider.Provider;
import com.example.nick.growerland.APIProvider.WeatherForecastResponse;
import com.example.nick.growerland.R;

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

    }
}
