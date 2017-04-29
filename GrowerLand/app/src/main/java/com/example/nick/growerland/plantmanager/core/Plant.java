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

    public void set(final String field, final String s) {
        if ("min_maturation".equals(field)) {
            mMinMaturation = Integer.parseInt(s);
        } else if ("max_maturation".equals(field)) {
            mMaxMaturation = Integer.parseInt(s);
        } else if ("min_temp".equals(field)) {
            mMinTemperature = Integer.parseInt(s);
        } else if ("max_temp".equals(field)) {
            mMaxTemperature = Integer.parseInt(s);
        } else if ("favor_temp".equals(field)) {
            mFavorTemperature = Integer.parseInt(s);
        } else if ("humidity".equals(field)) {
            mHumidity = Integer.parseInt(s);
        } else if ("watering_interval".equals(field)) {
            mWateringInterval = Integer.parseInt(s);
        } else if ("top_dressing_interval".equals(field)) {
            mTopDressingInterval = Integer.parseInt(s);
        }
    }

    public void setName(final String name) {
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
