package com.example.nick.growerland.plantmanager.core;

public class Plant {

    private String mName;
    private Integer mMinMaturation;
    private Integer mMaxMaturation;
    private Double mMinTemperature;
    private Double mMaxTemperature;
    private Double mFavorTemperature;
    private Double mHumidity;
    private Integer mWateringInterval;
    private Integer mTopDressingInterval;

    public void set(final String field, final String s) {
        if ("min_maturation".equals(field)) {
            mMinMaturation = Integer.parseInt(s);
        } else if ("max_maturation".equals(field)) {
            mMaxMaturation = Integer.parseInt(s);
        } else if ("min_temp".equals(field)) {
            mMinTemperature = Double.parseDouble(s);
        } else if ("max_temp".equals(field)) {
            mMaxTemperature = Double.parseDouble(s);
        } else if ("favor_temp".equals(field)) {
            mFavorTemperature = Double.parseDouble(s);
        } else if ("humidity".equals(field)) {
            mHumidity = Double.parseDouble(s);
        } else if ("watering_interval".equals(field)) {
            mWateringInterval = Integer.parseInt(s);
        } else if ("top_dressing_interval".equals(field)) {
            mTopDressingInterval = Integer.parseInt(s);
        } else if ("name".equals(field)) {
            mName = s;
        }
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

    public double getMinTemperature() {
        return mMinTemperature;
    }

    public double getMaxTemperature() {
        return mMaxTemperature;
    }

    public double getFavorTemperature() {
        return mFavorTemperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public int getWateringInterval() {
        return mWateringInterval;
    }

    public int getTopDressingInterval() {
        return mTopDressingInterval;
    }

}
