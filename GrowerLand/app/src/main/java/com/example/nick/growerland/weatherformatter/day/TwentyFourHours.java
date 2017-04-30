package com.example.nick.growerland.weatherformatter.day;

/**
 * Created by kiril on 29.04.2017.
 */

public class TwentyFourHours {

    private DayTime mDayTime;
    private NightTime mNightTime;
    private double mAvarageHumidity;
    private double mAvarageTemperature;
    private double mRainVolume;

    public TwentyFourHours(){
        mDayTime = new DayTime();
        mNightTime = new NightTime();
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

    public double getAvarageHumidity() {
        return mAvarageHumidity;
    }

    public void setAvarageHumidity(double mHumidity) {
        this.mAvarageHumidity = mHumidity;
    }

    public double getAvarageTemperature() {
        return mAvarageTemperature;
    }

    public void setAvarageTemperature(double mAvarageTemperature) {
        this.mAvarageTemperature = mAvarageTemperature;
    }

    public double getRainVolume() { return mRainVolume; }

    public void setRainVolume(double mRainVolume) { this.mRainVolume = mRainVolume; }
}
