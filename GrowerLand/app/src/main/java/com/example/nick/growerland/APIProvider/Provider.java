package com.example.nick.growerland.APIProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

public class Provider {

    private String FORECAST_REQUEST_CAP = "http://api.openweathermap.org/data/2.5/forecast?APPID=a0a985154ca51f6a63ebe62cd98561f4&";

    public WeatherForecastResponse getFiveDayForecast(double lat, double lot) {
        String json = getJSON(FORECAST_REQUEST_CAP + locationDots(lat, lot));
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeatherForecastResponse.class, new WeatherForecastDeserializer())
                .registerTypeAdapter(Weather.class, new WeatherDeserializer())
                .create();
        return gson.fromJson(json, WeatherForecastResponse.class);
    }

    private String getJSON(final String request) {
        HttpClient httpClient = new HttpClient();
        httpClient.execute(request);
        String json = "";
        try {
            json = httpClient.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return json;
    }

    private String locationDots(double lat, double lot) {
        return "lat=" + String.valueOf(lat) + "&lon=" + String.valueOf(lot);
    }

}
