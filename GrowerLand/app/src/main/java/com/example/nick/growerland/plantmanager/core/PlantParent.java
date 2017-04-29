package com.example.nick.growerland.plantmanager.core;

import java.util.ArrayList;

public class PlantParent extends Plant {
    
    private ArrayList<PlantSort> mSorts;

    public ArrayList<PlantSort> getSorts() {
        return mSorts;
    }

    public void setSorts(ArrayList<PlantSort> sorts) {
        mSorts = sorts;
    }
}
