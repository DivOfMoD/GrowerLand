package com.example.nick.growerland.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nick.growerland.R;
import com.example.nick.growerland.weatherformatter.WeatherFormatter;
import com.example.nick.growerland.weatherformatter.day.TwentyFourHours;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        double lat = 53.663735, lot = 23.825932; //Grondo location
//        WeatherForecastResponse fiveDayForecast = new Provider().getFiveDayForecast(lat, lot);
//        Log.d("TAG", fiveDayForecast.getWeather().get(0).getDate().toString());

//        WeatherFormatter weatherFormatter = new WeatherFormatter(lat, lot);
//        ArrayList<TwentyFourHours> twentyFourHourses = weatherFormatter.getTwentyFourHoursList();
    }

}
