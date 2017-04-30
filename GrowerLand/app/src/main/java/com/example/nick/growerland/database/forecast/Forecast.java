package com.example.nick.growerland.database.forecast;

class Forecast {

    private int mId;
    private float mTemperature;
    private float mHumidity;
    private float mWateringDaysLeft;
    private int mTopDressingDaysLeft;

    public int getId() {

        return mId;
    }

    public float getTemperature() {
        return mTemperature;
    }

    public float getHumidity() {
        return mHumidity;
    }

    public float getWateringDaysLeft() {
        return mWateringDaysLeft;
    }

    public int getTopDressingDaysLeft() {
        return mTopDressingDaysLeft;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTemperature(float temperature) {
        mTemperature = temperature;
    }

    public void setHumidity(float humidity) {
        mHumidity = humidity;
    }

    public void setWateringDaysLeft(float wateringDaysLeft) {
        mWateringDaysLeft = wateringDaysLeft;
    }

    public void setTopDressingDaysLeft(int topDressingDaysLeft) {
        mTopDressingDaysLeft = topDressingDaysLeft;
    }
}
