package com.github.maxcriser.grower.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.maxcriser.grower.APIProvider.Provider;
import com.github.maxcriser.grower.APIProvider.WeatherForecastResponse;
import com.github.maxcriser.grower.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        double lat = 53.663735, lot = 23.825932; //Grondo location
        WeatherForecastResponse fiveDayForecast = new Provider().getFiveDayForecast(lat, lot);
        Log.d("TAG", fiveDayForecast.getWeather().get(0).getDate().toString());
    }

}
