package com.example.nick.growerland.plantmanager.deserializers;

import com.example.nick.growerland.plantmanager.core.PlantCollection;
import com.example.nick.growerland.plantmanager.core.PlantParent;
import com.example.nick.growerland.plantmanager.core.PlantType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlantCollectionDeserializer implements JsonDeserializer<PlantCollection> {

    private final String[] ROOTS = {"veg"};

    @Override
    public PlantCollection deserialize(
            final JsonElement jsonElement,
            final Type typeOfT,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final PlantCollection response = new PlantCollection();
        final JsonObject root = jsonElement.getAsJsonObject();
        final ArrayList<PlantType> typeArray = new ArrayList<>();
        for (final String s :
                ROOTS) {
            final PlantType type = context.deserialize(root.get(s), PlantType.class);
            type.setType(s);
            typeArray.add(type);
        }

        response.setPlantTypes(typeArray);
        return response;
    }
}
