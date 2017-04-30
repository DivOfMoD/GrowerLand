package com.example.nick.growerland.weatherformatter;

import com.example.nick.growerland.database.forecast.Forecast;
import com.example.nick.growerland.plantmanager.core.PlantCollection;
import com.example.nick.growerland.plantmanager.core.PlantSort;
import com.example.nick.growerland.weatherformatter.day.TwentyFourHours;

import java.util.ArrayList;

public class WeatherAlgorithm {

    private ArrayList<TwentyFourHours> mHourses;

    public WeatherAlgorithm(ArrayList<TwentyFourHours> hourses) {
        mHourses = hourses;
    }

    public float getAverageTemperature() {
        float container = 0;
        for (final TwentyFourHours hours
                : mHourses
                ) {
            container += hours.getAvarageTemperature();
        }
        return container / mHourses.size();
    }

    public float getAverageHumidity() {
        float container = 0;
        for (final TwentyFourHours hours
                : mHourses
                ) {
            container += hours.getAvarageHumidity();
        }
        return container / mHourses.size();
    }

//    public float getAverage

    public void updateForecast(PlantCollection collection, Forecast forecast) {
        PlantSort sort = collection.getSortById(forecast.getId());
        float deltaTemp = forecast.getTemperature() - sort.getFavorTemperature();
        float deltaHum = forecast.getHumidity() - sort.getHumidity();
        if (deltaTemp > 0 && deltaHum < 0) {
            float percentTemp = deltaTemp * 100 / sort.getFavorTemperature();
            float percentHum = -deltaHum * 100 / sort.getHumidity();
            float percentOffset = (percentHum + percentTemp) / 2;
            float dayOffsetWater = sort.getWateringInterval() * percentOffset / 100;
            sort.setWateringInterval(sort.getWateringInterval() - dayOffsetWater);
        }
        if (getAverageTemperature() > sort.getMaxTemperature()) {
            sort.setWateringInterval(0f);
        }
        if (getAverageTemperature() < sort.getMinTemperature()) {
            //TODO: (Push Manager) push with warning about protect vegetables
        }
    }
}
