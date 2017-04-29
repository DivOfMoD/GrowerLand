package com.example.nick.growerland.plantmanager.deserializers;

import com.example.nick.growerland.plantmanager.core.PlantCollection;
import com.example.nick.growerland.plantmanager.core.PlantType;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlantCollectionDeserializer implements JsonDeserializer<PlantCollection> {

    @Override
    public PlantCollection deserialize(
            final JsonElement jsonElement,
            final Type typeOfT,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final PlantCollection response = new PlantCollection();
        final JsonArray jPlantTypes = jsonElement.getAsJsonArray();

        final ArrayList<PlantType> typeArray = new ArrayList<>();
        for (final JsonElement listItem :
                jPlantTypes) {
            typeArray.add(context.deserialize(listItem, PlantType.class));
        }

        response.setPlantTypes(typeArray);
        return response;
    }
}
