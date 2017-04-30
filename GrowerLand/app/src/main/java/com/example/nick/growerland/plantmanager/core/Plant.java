package com.example.nick.growerland.plantmanager.core;

public class Plant {

    private String mName;
    private Integer mMinMaturation;
    private Integer mMaxMaturation;
    private Float mMinTemperature;
    private Float mMaxTemperature;
    private Float mFavorTemperature;
    private Float mHumidity;
    private Float mWateringInterval;
    private Float mTopDressingInterval;

    public void set(final String field, final String s) {
        if ("min_maturation".equals(field)) {
            mMinMaturation = Integer.parseInt(s);
        } else if ("max_maturation".equals(field)) {
            mMaxMaturation = Integer.parseInt(s);
        } else if ("min_temp".equals(field)) {
            mMinTemperature = Float.parseFloat(s);
        } else if ("max_temp".equals(field)) {
            mMaxTemperature = Float.parseFloat(s);
        } else if ("favor_temp".equals(field)) {
            mFavorTemperature = Float.parseFloat(s);
        } else if ("humidity".equals(field)) {
            mHumidity = Float.parseFloat(s);
        } else if ("watering_interval".equals(field)) {
            mWateringInterval = Float.parseFloat(s);
        } else if ("top_dressing_interval".equals(field)) {
            mTopDressingInterval = Float.parseFloat(s);
        } else if ("name".equals(field)) {
            mName = s;
        }
    }

    public String getName() {
        return mName;
    }

    public Integer getMinMaturation() {
        return mMinMaturation;
    }

    public Integer getMaxMaturation() {
        return mMaxMaturation;
    }

    public Float getMinTemperature() {
        return mMinTemperature;
    }

    public Float getMaxTemperature() {
        return mMaxTemperature;
    }

    public Float getFavorTemperature() {
        return mFavorTemperature;
    }

    public Float getHumidity() {
        return mHumidity;
    }

    public Float getWateringInterval() {
        return mWateringInterval;
    }

    public Float getTopDressingInterval() {
        return mTopDressingInterval;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setMinMaturation(Integer minMaturation) {
        mMinMaturation = minMaturation;
    }

    public void setMaxMaturation(Integer maxMaturation) {
        mMaxMaturation = maxMaturation;
    }

    public void setMinTemperature(Float minTemperature) {
        mMinTemperature = minTemperature;
    }

    public void setMaxTemperature(Float maxTemperature) {
        mMaxTemperature = maxTemperature;
    }

    public void setFavorTemperature(Float favorTemperature) {
        mFavorTemperature = favorTemperature;
    }

    public void setHumidity(Float humidity) {
        mHumidity = humidity;
    }

    public void setWateringInterval(Float wateringInterval) {
        mWateringInterval = wateringInterval;
    }

    public void setTopDressingInterval(Float topDressingInterval) {
        mTopDressingInterval = topDressingInterval;
    }
}
