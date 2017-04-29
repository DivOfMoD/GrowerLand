package com.example.nick.growerland.weatherformatter;

import com.example.nick.growerland.APIProvider.Provider;
import com.example.nick.growerland.APIProvider.Weather;
import com.example.nick.growerland.APIProvider.WeatherForecastResponse;
import com.example.nick.growerland.weatherformatter.day.TwentyFourHours;
import com.example.nick.growerland.weatherformatter.day.DayTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kiril on 29.04.2017.
 */

public class WeatherFormatter {

    private Provider provider;
    private WeatherForecastResponse weatherForecastResponse;
    private ArrayList<Weather> mWeatherList;
    private ArrayList<TwentyFourHours> mTwentyFourHoursList;

    public WeatherFormatter(double lat, double lot){
        provider = new Provider();
        mTwentyFourHoursList = new ArrayList<>();
        weatherForecastResponse = provider.getFiveDayForecast(lat, lot);
        mWeatherList = weatherForecastResponse.getWeather();

        DivideIntoDays();
    }

    private void DivideIntoDays(){
        Calendar previousDate = Calendar.getInstance();
        previousDate.setTime(mWeatherList.get(0).getDate());
        TwentyFourHours twentyFourHours = new TwentyFourHours();
        for (Weather weather : mWeatherList){
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(weather.getDate());
            if(previousDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) {
                twentyFourHours.getWeatherList().add(weather);
            }
            else{
                previousDate = currentDate;
                mTwentyFourHoursList.add(twentyFourHours);
                twentyFourHours = new TwentyFourHours();
                twentyFourHours.getWeatherList().add(weather);
            }
        }
    }

    private void DivideIntoDayNightTimes(){

    }

    public ArrayList<TwentyFourHours> getTwentyFourHoursList() {
        return mTwentyFourHoursList;
    }

}
