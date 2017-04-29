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
}
