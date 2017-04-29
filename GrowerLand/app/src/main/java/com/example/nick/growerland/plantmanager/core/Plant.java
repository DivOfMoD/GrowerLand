package com.example.nick.growerland.plantmanager.core;

public class Plant {

    private String mName;
    private int mMinMaturation;
    private int mMaxMaturation;
    private int mMinTemperature;
    private int mMaxTemperature;
    private int mFavorTemperature;
    private int mHumidity;
    private int mWateringInterval;
    private int mTopDressingInterval;

    public void setMinMaturation(int minMaturation) {
        mMinMaturation = minMaturation;
    }

    public void setMaxMaturation(int maxMaturation) {
        mMaxMaturation = maxMaturation;
    }

    public void setMinTemperature(int minTemperture) {
        mMinTemperature = minTemperture;
    }

    public void setMaxTemperature(int maxTemperature) {
        mMaxTemperature = maxTemperature;
    }

    public void setFavorTemperature(int favorTemperature) {
        mFavorTemperature = favorTemperature;
    }

    public void setHumidity(int humidity) {
        mHumidity = humidity;
    }

    public void setWateringInterval(int wateringInterval) {
        mWateringInterval = wateringInterval;
    }

    public void setTopDressingInterval(int topDressingInterval) {
        mTopDressingInterval = topDressingInterval;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public int getMinMaturation() {
        return mMinMaturation;
    }

    public int getMaxMaturation() {
        return mMaxMaturation;
    }

    public int getMinTemperature() {
        return mMinTemperature;
    }

    public int getMaxTemperature() {
        return mMaxTemperature;
    }

    public int getFavorTemperature() {
        return mFavorTemperature;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public int getWateringInterval() {
        return mWateringInterval;
    }

    public int getTopDressingInterval() {
        return mTopDressingInterval;
    }

}
