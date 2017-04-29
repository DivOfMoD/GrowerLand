package com.example.nick.growerland.plantmanager.core;

import java.util.ArrayList;

public class PlantParent extends Plant {
    
    private ArrayList<Plant> mSorts;

    public ArrayList<Plant> getSorts() {
        return mSorts;
    }

    public void setSorts(ArrayList<Plant> sorts) {
        mSorts = sorts;
    }
}
