package com.example.nick.growerland.weatherformatter.day;

import com.example.nick.growerland.APIProvider.Weather;

import java.util.ArrayList;

/**
 * Created by kiril on 29.04.2017.
 */

public class DayTime{

    private double mAvarageTemperature;
    private ArrayList<Weather> mWeatherList;

    public double getAvarageTemperature() {
        return mAvarageTemperature;
    }

    public void setAvarageTemperature(double mAvarageTemperature) {
        this.mAvarageTemperature = mAvarageTemperature;
    }

    public ArrayList<Weather> getWeatherList() {
        return mWeatherList;
    }

    public void setWeatherList(ArrayList<Weather> mWeatherList) {
        this.mWeatherList = mWeatherList;
    }
}
