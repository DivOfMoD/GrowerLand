package com.example.nick.growerland.database;

import android.content.Context;
import android.database.Cursor;

import com.example.nick.growerland.database.forecast.Forecast;
import com.example.nick.growerland.database.forecast.ForecastTable;
import com.example.nick.growerland.plantmanager.core.PlantSort;
import com.example.nick.growerland.weatherformatter.WeatherAlgorithm;
import com.example.nick.growerland.weatherformatter.WeatherFormatter;

public class DatabaseManager {

    private Context mContext;

    public DatabaseManager(Context context) {
        mContext = context;
    }

    public void addPlant(Cursor cursor, PlantSort plantSort) { // TODO: 30.04.2017 pass Cursor HERE!!
        Forecast forecast = new Forecast();
        forecast.setId(plantSort.getId());
        forecast.setWateringDaysLeft(plantSort.getWateringInterval());
        forecast.setTopDressingDaysLeft(plantSort.getTopDressingInterval().intValue());
        cursor.moveToFirst();
        double lat = Double.parseDouble(cursor.getString(3));
        double lon = Double.parseDouble(cursor.getString(4));
        final WeatherFormatter weatherFormatter = new WeatherFormatter(lat, lon);
        WeatherAlgorithm algorithm = new WeatherAlgorithm(weatherFormatter.getTwentyFourHoursList());
        float avTemp = algorithm.getAverageTemperature();
        float avHum = algorithm.getAverageHumidity();
        forecast.setHumidity(avHum);
        forecast.setTemperature(avTemp);
        new ForecastTable().insert(mContext, forecast);
    }

}
