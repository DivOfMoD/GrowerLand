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

            calculateWeatherForDayTime(twentyFourHours.getDayTime());
            calculateWeatherForNightTime(twentyFourHours.getNightTime());
            calculateWeatherForTwentyFourHours(twentyFourHours);
        }
    }

    private void calculateWeatherForDayTime(DayTime dayTime) {
        double avarageTemperature = 0;
        double avarageHumidity = 0;
        double rainVolume = 0;

        for (Weather weather : dayTime.getWeatherList()) {
            avarageHumidity += weather.getHumidity();
            avarageTemperature += (weather.getTemperature() - 273);
            rainVolume += weather.getRainVolume();
        }

        dayTime.setAvarageTemperature(
                avarageTemperature / dayTime.getWeatherList().size());
        dayTime.setAvarageHumidity(
                avarageHumidity / dayTime.getWeatherList().size());
        dayTime.setRainVolume(rainVolume);
    }

    private void calculateWeatherForNightTime(NightTime nightTime) {
        double avarageTemperature = 0;
        double avarageHumidity = 0;
        double rainVolume = 0;

        for (Weather weather : nightTime.getWeatherList()) {
            avarageHumidity += weather.getHumidity();
            avarageTemperature += (weather.getTemperature() - 273);
            rainVolume += weather.getRainVolume();
        }

        nightTime.setAvarageTemperature(
                avarageTemperature / nightTime.getWeatherList().size());
        nightTime.setAvarageHumidity(
                avarageHumidity / nightTime.getWeatherList().size());
        nightTime.setRainVolume(rainVolume);
    }

    private void calculateWeatherForTwentyFourHours(TwentyFourHours twentyFourHours){
        twentyFourHours.setAvarageTemperature(
                (twentyFourHours.getDayTime().getAvarageTemperature() +
                        twentyFourHours.getNightTime().getAvarageTemperature()) / 2);
        twentyFourHours.setAvarageHumidity(
                (twentyFourHours.getDayTime().getAvarageHumidity() +
                        twentyFourHours.getNightTime().getAvarageHumidity()) / 2);
        twentyFourHours.setRainVolume(
                twentyFourHours.getDayTime().getRainVolume() +
                        twentyFourHours.getNightTime().getRainVolume());
    }
}
