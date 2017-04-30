package com.example.nick.growerland.weatherapi;

import com.example.nick.growerland.jsonprovider.Provider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WeatherProvider {

    private String FORECAST_REQUEST_CAP = "http://api.openweathermap.org/data/2.5/forecast?APPID=a0a985154ca51f6a63ebe62cd98561f4&";

    public WeatherForecastResponse getFiveDayForecast(double lat, double lot) {
        String json = new Provider().getJson(FORECAST_REQUEST_CAP + locationDots(lat, lot));
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherForecastResponse.class, new WeatherForecastDeserializer())
                .registerTypeAdapter(Weather.class, new WeatherDeserializer())
                .create();
        return gson.fromJson(json, WeatherForecastResponse.class);
    }

    private String locationDots(double lat, double lot) {
        return "lat=" + String.valueOf(lat) + "&lon=" + String.valueOf(lot);
    }

}
