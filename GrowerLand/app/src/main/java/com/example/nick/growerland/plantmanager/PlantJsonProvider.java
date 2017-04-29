package com.example.nick.growerland.plantmanager;

import com.example.nick.growerland.plantmanager.core.PlantCollection;
import com.example.nick.growerland.plantmanager.core.PlantParent;
import com.example.nick.growerland.plantmanager.core.PlantSort;
import com.example.nick.growerland.plantmanager.core.PlantType;
import com.example.nick.growerland.plantmanager.deserializers.PlantCollectionDeserializer;
import com.example.nick.growerland.plantmanager.deserializers.PlantSortDeserializer;
import com.example.nick.growerland.plantmanager.deserializers.PlantParentDeserializer;
import com.example.nick.growerland.plantmanager.deserializers.PlantTypeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PlantJsonProvider {

    public PlantCollection getPlantCollection(final String path) {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlantCollection.class, new PlantCollectionDeserializer())
                .registerTypeAdapter(PlantType.class, new PlantTypeDeserializer())
                .registerTypeAdapter(PlantParent.class, new PlantParentDeserializer())
                .registerTypeAdapter(PlantSort.class, new PlantSortDeserializer())
                .create();
        return gson.fromJson(path, PlantCollection.class);
    }

}
