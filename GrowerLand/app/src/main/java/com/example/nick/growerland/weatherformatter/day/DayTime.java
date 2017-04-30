package com.example.nick.growerland.weatherformatter.day;

import com.example.nick.growerland.weatherapi.Weather;

import java.util.ArrayList;

/**
 * Created by kiril on 29.04.2017.
 */

public class DayTime{

    private double mAvarageTemperature;
    private double mAvarageHumidity;
    private double mRainVolume;
    private ArrayList<Weather> mWeatherList;

    public DayTime(){
        mWeatherList = new ArrayList<>();
    }

    public double getAvarageTemperature() {
        return mAvarageTemperature;
    }

    public void setAvarageTemperature(double mAvarageTemperature) {
        this.mAvarageTemperature = mAvarageTemperature;
    }

    public double getAvarageHumidity() {
        return mAvarageHumidity;
    }

    public void setAvarageHumidity(double mAvarageHumidity) {
        this.mAvarageHumidity = mAvarageHumidity;
    }

    public double getRainVolume() {
        return mRainVolume;
    }

    public void setRainVolume(double mRainVolume) {
        this.mRainVolume = mRainVolume;
    }

    public ArrayList<Weather> getWeatherList() {
        return mWeatherList;
    }
}
