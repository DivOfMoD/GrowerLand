package com.example.nick.growerland.plantmanager.deserializers;

import com.example.nick.growerland.plantmanager.core.PlantParent;
import com.example.nick.growerland.plantmanager.core.PlantSort;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
        response.setName(key);
        final JsonObject jParentDescription = jsonObject.getAsJsonObject(key);

        final ArrayList<PlantSort> typeArray = new ArrayList<>();
        final Iterator<Map.Entry<String, JsonElement>> iterator = jParentDescription
                .entrySet().iterator();
        final String array = iterator.next().getKey();
        while (iterator.hasNext()) {
            final Map.Entry<String, JsonElement> entry = iterator.next();
            response.set(entry.getKey(), entry.getValue().getAsString());
        }
        for (final JsonElement listItem :
                jParentDescription.getAsJsonArray(array)) {
            final PlantSort sort = context.deserialize(listItem, PlantSort.class);
            sort.setPlantParent(response);
            typeArray.add(sort);
        }

        response.setSorts(typeArray);
        return response;
    }
}
