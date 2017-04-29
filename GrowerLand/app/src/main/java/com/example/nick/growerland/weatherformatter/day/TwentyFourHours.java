package com.example.nick.growerland.weatherformatter.day;

import com.example.nick.growerland.APIProvider.Weather;

import java.util.ArrayList;

/**
 * Created by kiril on 29.04.2017.
 */

public class TwentyFourHours {

    private DayTime mDayTime;
    private NightTime mNightTime;
    private double mAvarageHumidity;
    private double mAvarageTemperature;
    private ArrayList<Weather> mWeatherList;

    public TwentyFourHours(){
        mWeatherList = new ArrayList<>();
    }

    public DayTime getDayTime() {
        return mDayTime;
    }

    public void setDayTime(DayTime mDayTime) {
        this.mDayTime = mDayTime;
    }

    public NightTime getNightTime() {
        return mNightTime;
    }

    public void setNightTime(NightTime mNightTime) {
        this.mNightTime = mNightTime;
    }

    public double getHumidity() {
        return mAvarageHumidity;
    }

    public void setHumidity(double mHumidity) {
        this.mAvarageHumidity = mHumidity;
    }

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
