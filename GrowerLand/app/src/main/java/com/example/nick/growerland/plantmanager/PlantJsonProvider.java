package com.example.nick.growerland.plantmanager;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.nick.growerland.jsonprovider.Provider;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlantJsonProvider {

    private final String FILE_NAME = "file_name";

    public PlantCollection getJsonCollection(final Context context, final String path) {
        final String json = new Provider().getJson(path);
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlantCollection.class, new PlantCollectionDeserializer())
                .registerTypeAdapter(PlantType.class, new PlantTypeDeserializer())
                .registerTypeAdapter(PlantParent.class, new PlantParentDeserializer())
                .registerTypeAdapter(PlantSort.class, new PlantSortDeserializer())
                .create();
        PlantCollection collection = gson.fromJson(json, PlantCollection.class);
        if (collection != null) {
            writeJson(context, json);
        } else {
            collection = getCachedCollection(context, gson);
        }
        return collection;
    }


    private PlantCollection getCachedCollection(final Context context, Gson gson) {
        BufferedReader input = null;
        File file = null;
        PlantCollection collection = null;
        try {
            file = new File(context.getCacheDir(), FILE_NAME);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            final StringBuilder buffer = new StringBuilder();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            collection = gson.fromJson(buffer.toString(), PlantCollection.class);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return collection;
    }

    private void writeJson(final Context context, final String json) {
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
