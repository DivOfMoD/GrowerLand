package com.example.nick.growerland.plantmanager.core;

import java.util.ArrayList;

public class PlantCollection {

    private ArrayList<PlantType> mPlantTypes;

    public ArrayList<PlantType> getPlantTypes() {
        return mPlantTypes;
    }

    public void setPlantTypes(final ArrayList<PlantType> plantTypes) {
        mPlantTypes = plantTypes;
    }

    public PlantSort getSortById(int id) {
        for (PlantType type :
                mPlantTypes) {
            for (PlantParent parent:
                 type.getPlantParents()) {
                for (PlantSort sort:
                        parent.getSorts()) {
                    if (sort.getId() == id) {
                        return sort;
                    }
                }
            }
        }
        return null;
    }
}
