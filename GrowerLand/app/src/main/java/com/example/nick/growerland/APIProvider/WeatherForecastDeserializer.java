package com.example.nick.growerland.APIProvider;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


class WeatherForecastDeserializer extends ValidationChecker implements JsonDeserializer<WeatherForecastResponse> {

    @Override
    public WeatherForecastResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        WeatherForecastResponse response = new WeatherForecastResponse();

        JsonObject rootObject = jsonElement.getAsJsonObject();
        JsonArray jWeatherList = rootObject.getAsJsonArray("list");


        ArrayList<Weather> weatherArray = new ArrayList<>();
        for (JsonElement listItem :
                jWeatherList) {
            weatherArray.add(context.deserialize(listItem, Weather.class));
        }

        response.setCod(checkNumValidation(rootObject, "cod").getAsInt());
        response.setMessage(checkNumValidation(rootObject, "message").getAsDouble());
        response.setCnt(checkNumValidation(rootObject, "cnt").getAsInt());
        response.setWeather(weatherArray);

        return response;
    }

}
