package com.example.nick.growerland.plantmanager.deserializers;

import com.example.nick.growerland.plantmanager.core.Plant;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PlantDeserializer implements JsonDeserializer<Plant> {

    @Override
    public Plant deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        return null;
    }
}
