package com.example.nick.growerland.plantmanager.core;

public class PlantSort extends Plant {

    private PlantParent mPlantParent;
    private int id;

    public Plant getPlantParent() {
        return mPlantParent;
    }

    public int getId() {
        return id;
    }

    public void setPlantParent(final PlantParent plantParent) {
        mPlantParent = plantParent;
    }

    public void set(final String field, final String s) {
        super.set(field, s);
        if ("id".equals(field)) {
            id = Integer.parseInt(s);
        }
    }

    @Override
    public Integer getMinMaturation() {
        final Integer value = super.getMinMaturation();
        if (value == null) {
            return mPlantParent.getMinMaturation();
        }
        return value;
    }

    @Override
    public Integer getMaxMaturation() {
        final Integer value = super.getMaxMaturation();
        if (value == null) {
            return mPlantParent.getMaxMaturation();
        }
        return value;
    }

    @Override
    public Float getMinTemperature() {
        final Float value = super.getMinTemperature();
        if (value == null) {
            return mPlantParent.getMinTemperature();
        }
        return value;
    }

    @Override
    public Float getMaxTemperature() {
        final Float value = super.getMaxTemperature();
        if (value == null) {
            return mPlantParent.getMaxTemperature();
        }
        return value;
    }

    @Override
    public Float getFavorTemperature() {
        final Float value = super.getFavorTemperature();
        if (value == null) {
            return mPlantParent.getFavorTemperature();
        }
        return value;
    }

    @Override
    public Float getHumidity() {
        final Float value = super.getHumidity();
        if (value == null) {
            return mPlantParent.getHumidity();
        }
        return value;
    }

    @Override
    public Float getWateringInterval() {
        final Float value = super.getWateringInterval();
        if (value == null) {
            return mPlantParent.getWateringInterval();
        }
        return value;
    }

    @Override
    public Float getTopDressingInterval() {
        final Float value = super.getTopDressingInterval();
        if (value == null) {
            return mPlantParent.getTopDressingInterval();
        }
        return value;
    }
}
