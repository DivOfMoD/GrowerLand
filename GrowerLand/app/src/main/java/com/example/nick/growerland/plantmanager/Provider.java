package com.example.nick.growerland.plantmanager;

import com.example.nick.growerland.plantmanager.core.Plant;
import com.example.nick.growerland.plantmanager.core.PlantCollection;
import com.example.nick.growerland.plantmanager.core.PlantParent;
import com.example.nick.growerland.plantmanager.core.PlantType;
import com.example.nick.growerland.plantmanager.deserializers.PlantCollectionDeserializer;
import com.example.nick.growerland.plantmanager.deserializers.PlantDeserializer;
import com.example.nick.growerland.plantmanager.deserializers.PlantParentDeserializer;
import com.example.nick.growerland.plantmanager.deserializers.PlantTypeDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Provider {

    public PlantCollection getPlantCollection() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlantCollection.class, new PlantCollectionDeserializer())
                .registerTypeAdapter(PlantType.class, new PlantTypeDeserializer())
                .registerTypeAdapter(PlantParent.class, new PlantParentDeserializer())
                .registerTypeAdapter(Plant.class, new PlantDeserializer())
                .create();
        return gson.fromJson(JsonTest.DATA, PlantCollection.class);
    }

}
