package com.github.maxcriser.grower.APIProvider;

import java.util.ArrayList;

public class WeatherForecastResponse {

    //Parsed analogy of JSON class received on http request
    private int cod;
    private double message;
    private int cnt;
    private ArrayList<Weather> mWeather;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<Weather> getWeather() {
        return mWeather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        mWeather = weather;
    }

    public void addWeather(Weather weather) {
        mWeather.add(weather);
    }
}
