package com.example.nick.growerland.plantmanager.deserializers;

import com.example.nick.growerland.plantmanager.core.Plant;
import com.example.nick.growerland.plantmanager.core.PlantParent;
import com.example.nick.growerland.plantmanager.core.PlantType;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlantParentDeserializer implements JsonDeserializer<PlantParent> {

    @Override
    public PlantParent deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final PlantParent response = new PlantParent();
        final JsonObject jsonObject = json.getAsJsonObject();
        final String key = jsonObject.entrySet().iterator().next().getKey();
        final JsonArray jPlantTypes = jsonObject.getAsJsonObject(key).getAsJsonArray();

        final ArrayList<Plant> typeArray = new ArrayList<>();
        for (final JsonElement listItem :
                jPlantTypes) {
            typeArray.add(context.deserialize(listItem, Plant.class));
        }

        response.setName(key);
        response.setSorts(typeArray);
        return response;
    }
}
