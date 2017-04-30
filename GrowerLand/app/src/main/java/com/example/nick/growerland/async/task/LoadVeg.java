package com.example.nick.growerland.async.task;

import android.content.Context;

import com.example.nick.growerland.async.ProgressCallback;
import com.example.nick.growerland.async.Task;
import com.example.nick.growerland.plantmanager.PlantJsonProvider;
import com.example.nick.growerland.plantmanager.core.PlantParent;

import java.util.ArrayList;

public class LoadVeg implements Task<Context, Void, ArrayList<String>> {

    @Override
    public ArrayList<String> doInBackground(final Context pContext, final ProgressCallback<Void> pVoidProgressCallback) throws Exception {
        final ArrayList<String> planetList = new ArrayList();

        ArrayList<PlantParent> arrayList = new PlantJsonProvider().getJsonCollection(pContext, "http://growerlandnasa.appspot.com/config").getPlantTypes().get(0).getPlantParents();

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.get(i).getSorts().size(); j++) {
                String name = arrayList.get(i).getName() + " | " + arrayList.get(i).getSorts().get(j).getName();
                planetList.add(name);
            }
        }

        return planetList;
    }
}