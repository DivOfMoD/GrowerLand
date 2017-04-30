package com.example.nick.growerland.plantmanager.core;

import java.util.ArrayList;

public class PlantType {

    private String mType;
    private ArrayList<PlantParent> mPlantParents;

    public ArrayList<PlantParent> getPlantParents() {
        return mPlantParents;
    }

    public void setPlantParents(final ArrayList<PlantParent> plantParents) {
        mPlantParents = plantParents;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
