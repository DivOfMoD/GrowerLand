package com.example.nick.growerland.plantmanager.deserializers;

import com.example.nick.growerland.plantmanager.core.PlantSort;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class PlantSortDeserializer implements JsonDeserializer<PlantSort> {

    @Override
    public PlantSort deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final PlantSort response = new PlantSort();
        for (final Map.Entry<String, JsonElement> entry :
                json.getAsJsonObject().entrySet()) {
            try {
                response.set(entry.getKey(), entry.getValue().getAsString());
            } catch (final Exception ignored) {
            }
        }
        return response;
    }
}
