package com.example.nick.growerland.weatherformatter;

import com.example.nick.growerland.APIProvider.Weather;
import com.example.nick.growerland.weatherformatter.day.DayTime;
import com.example.nick.growerland.weatherformatter.day.NightTime;
import com.example.nick.growerland.weatherformatter.day.TwentyFourHours;

import java.util.ArrayList;

/**
 * Created by kiril on 29.04.2017.
 */

public class WeatherCalculation {

    public WeatherCalculation(ArrayList<TwentyFourHours> twentyFourHoursList){
        calculate(twentyFourHoursList);
    }

    private void calculate(ArrayList<TwentyFourHours> twentyFourHoursList){
        for (TwentyFourHours twentyFourHours : twentyFourHoursList){

            calculateAvarageTemperatureAndHumidityForDayTime(twentyFourHours.getDayTime());
            calculateAvarageTemperatureAndHumidityForNightTime(twentyFourHours.getNightTime());
            calculateAvarageTemperatureAndHumidityForTwentyFourHours(twentyFourHours);
        }
    }

    private void calculateAvarageTemperatureAndHumidityForDayTime(DayTime dayTime) {
        double avarageTemperature = 0;
        double avarageHumidity = 0;

        for (Weather wheather : dayTime.getWeatherList()) {
            avarageHumidity += wheather.getHumidity();
            avarageTemperature += (wheather.getTemperature() - 273);
        }

        dayTime.setAvarageTemperature(
                avarageTemperature / dayTime.getWeatherList().size());
        dayTime.setAvarageHumidity(
                avarageHumidity / dayTime.getWeatherList().size());
    }

    private void calculateAvarageTemperatureAndHumidityForNightTime(NightTime nightTime) {
        double avarageTemperature = 0;
        double avarageHumidity = 0;

        for (Weather wheather : nightTime.getWeatherList()) {
            avarageHumidity += wheather.getHumidity();
            avarageTemperature += (wheather.getTemperature() - 273);
        }

        nightTime.setAvarageTemperature(
                avarageTemperature / nightTime.getWeatherList().size());
        nightTime.setAvarageHumidity(
                avarageHumidity / nightTime.getWeatherList().size());
    }

    private void calculateAvarageTemperatureAndHumidityForTwentyFourHours(TwentyFourHours twentyFourHours){
        twentyFourHours.setAvarageTemperature(
                (twentyFourHours.getDayTime().getAvarageTemperature() +
                        twentyFourHours.getNightTime().getAvarageTemperature()) / 2);
        twentyFourHours.setAvarageHumidity(
                (twentyFourHours.getDayTime().getAvarageHumidity() +
                        twentyFourHours.getNightTime().getAvarageHumidity()) / 2);
    }
}
