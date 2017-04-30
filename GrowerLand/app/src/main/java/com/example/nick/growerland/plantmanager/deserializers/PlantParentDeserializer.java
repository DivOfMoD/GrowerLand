package com.example.nick.growerland.plantmanager.deserializers;

import com.example.nick.growerland.plantmanager.core.PlantParent;
import com.example.nick.growerland.plantmanager.core.PlantSort;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class PlantParentDeserializer implements JsonDeserializer<PlantParent> {

    @Override
    public PlantParent deserialize(
            final JsonElement json,
            final Type typeOfT,
            final JsonDeserializationContext context
    ) throws JsonParseException {
        final PlantParent response = new PlantParent();
        for (final Map.Entry<String, JsonElement> entry :
                json.getAsJsonObject().entrySet()) {
            try {
                response.set(entry.getKey(), entry.getValue().getAsString());
            } catch (final Exception ignored) {
            }
        }
        final ArrayList<PlantSort> sorts = new ArrayList<>();
        for (final JsonElement jsonElement :
                json.getAsJsonObject().get("sorts").getAsJsonArray()) {
            final PlantSort sort = context.deserialize(jsonElement, PlantSort.class);
            sort.setPlantParent(response);
            sorts.add(sort);
        }
        response.setSorts(sorts);
        return response;
    }
}
