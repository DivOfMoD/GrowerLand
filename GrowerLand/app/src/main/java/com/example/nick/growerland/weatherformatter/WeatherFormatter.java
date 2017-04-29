package com.example.nick.growerland.weatherformatter;

import com.example.nick.growerland.APIProvider.Provider;
import com.example.nick.growerland.APIProvider.Weather;
import com.example.nick.growerland.APIProvider.WeatherForecastResponse;
import com.example.nick.growerland.weatherformatter.day.TwentyFourHours;
import com.example.nick.growerland.weatherformatter.day.DayTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeatherFormatter {

    private Provider provider;
    private WeatherForecastResponse weatherForecastResponse;
    private ArrayList<Weather> mWeatherList;
    private ArrayList<TwentyFourHours> mTwentyFourHoursList;

    public WeatherFormatter(){
        provider = new Provider();
        mTwentyFourHoursList = new ArrayList<>();
    }

    public WeatherFormatter(double lat, double lot){
        provider = new Provider();
        mTwentyFourHoursList = new ArrayList<>();
        form(lat, lot);
    }

    public void form(double lat, double lot){
        weatherForecastResponse = provider.getFiveDayForecast(lat, lot);
        mWeatherList = weatherForecastResponse.getWeather();
        divideIntoTwentyFourHours();
        WeatherCalculation weatherCalculation = new WeatherCalculation(mTwentyFourHoursList);
    }

    private void divideIntoTwentyFourHours(){
        Calendar previousDate = Calendar.getInstance();
        previousDate.setTime(mWeatherList.get(0).getDate());
        TwentyFourHours twentyFourHours = new TwentyFourHours();

        for (Weather weather : mWeatherList){
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTime(weather.getDate());
            if(previousDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) {
                divideIntoDayNightTimes(currentDate, weather, twentyFourHours);
            }
            else{
                previousDate = currentDate;
                mTwentyFourHoursList.add(twentyFourHours);
                twentyFourHours = new TwentyFourHours();
                divideIntoDayNightTimes(currentDate, weather, twentyFourHours);
            }
        }
        mTwentyFourHoursList.add(twentyFourHours);
    }

    private void divideIntoDayNightTimes(Calendar currentDate, Weather weather, TwentyFourHours twentyFourHours){
        if(currentDate.get(Calendar.HOUR_OF_DAY) >= 9 && currentDate.get(Calendar.HOUR_OF_DAY) < 24){
            twentyFourHours.getDayTime().getWeatherList().add(weather);
        }
        else {
            twentyFourHours.getNightTime().getWeatherList().add(weather);
        }
    }

    public ArrayList<TwentyFourHours> getTwentyFourHoursList() {
        return mTwentyFourHoursList;
    }

}
