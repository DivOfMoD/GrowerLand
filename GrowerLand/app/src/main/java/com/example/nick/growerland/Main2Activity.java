package com.example.nick.growerland;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.nick.growerland.weatherformatter.WeatherFormatter;
import com.example.nick.growerland.weatherformatter.day.TwentyFourHours;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        WeatherFormatter weatherFormatter = new WeatherFormatter(53.6884000, 23.8258000);
        for (TwentyFourHours twentyFourHours : weatherFormatter.getTwentyFourHoursList()){
            Log.d("TAG", String.valueOf(twentyFourHours.getWeatherList().get(0).getDate().getDay()));

        }
    }
}
