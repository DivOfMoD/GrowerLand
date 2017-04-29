package com.example.nick.growerland;

import android.util.Log;

import com.example.nick.growerland.weatherformatter.WeatherFormatter;
import com.example.nick.growerland.weatherformatter.day.TwentyFourHours;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kiril on 29.04.2017.
 */
public class Main2ActivityTest {

    @Test
    public void main() throws Exception {
        WeatherFormatter weatherFormatter = new WeatherFormatter(53.6884000, 23.8258000);
        for (TwentyFourHours twentyFourHours : weatherFormatter.getTwentyFourHoursList()){
            Log.d("", String.valueOf(twentyFourHours.getWeatherList().get(0).getDate().getDay()));

        }


    }
}