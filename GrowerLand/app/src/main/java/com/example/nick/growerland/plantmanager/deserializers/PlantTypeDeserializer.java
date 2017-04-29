package com.example.nick.growerland.plantmanager.deserializers;

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

public class PlantTypeDeserializer implements JsonDeserializer<PlantType> {

    @Override
    public PlantType deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context
    ) throws JsonParseException {

        final PlantType response = new PlantType();
        final JsonObject jsonObject = json.getAsJsonObject();
        final String key = jsonObject.entrySet().iterator().next().getKey();
        final JsonArray jPlantTypes = jsonObject.getAsJsonObject(key).getAsJsonArray();

        final ArrayList<PlantParent> typeArray = new ArrayList<>();
        for (final JsonElement listItem :
                jPlantTypes) {
            typeArray.add(context.deserialize(listItem, PlantParent.class));
        }

        response.setType(key);
        response.setPlantParents(typeArray);
        return response;
    }
}
