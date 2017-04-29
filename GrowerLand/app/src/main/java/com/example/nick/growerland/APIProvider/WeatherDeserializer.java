package com.example.nick.growerland.APIProvider;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

class WeatherDeserializer extends ValidationChecker implements JsonDeserializer<Weather> {

    @Override
    public Weather deserialize(final JsonElement jsonElement, final Type type,
                               final JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        final JsonObject jWeatherObject = jsonElement.getAsJsonObject();
        final JsonObject jMain = jWeatherObject.getAsJsonObject("main");
        final JsonObject jWeather = jWeatherObject.getAsJsonArray("weather").get(0).getAsJsonObject();
        final JsonObject jWind = jWeatherObject.getAsJsonObject("wind");

        final Weather weather = new Weather();

        weather.setDate(new Date(jWeatherObject.get("dt").getAsLong() *1000));

        weather.setTemperature(checkNumValidation(jMain, "temp").getAsDouble());
        weather.setMinTemperature(checkNumValidation(jMain, "temp_min").getAsDouble());
        weather.setMaxTemperature(checkNumValidation(jMain, "temp_max").getAsDouble());
        weather.setPressure(checkNumValidation(jMain, "pressure").getAsDouble());
        weather.setSeaLevel(checkNumValidation(jMain, "sea_level").getAsDouble());
        weather.setGroundLevel(checkNumValidation(jMain, "grnd_level").getAsDouble());
        weather.setHumidity(checkNumValidation(jMain, "humidity").getAsInt());
        weather.setTemperature_kf(checkNumValidation(jMain, "temp_kf").getAsDouble());

        weather.setWeatherId(checkNumValidation(jWeather, "id").getAsInt());
        weather.setWeatherMain(checkStrValidation(jWeather, "main").getAsString());
        weather.setWeatherDescription(checkStrValidation(jWeather, "main").getAsString());
        weather.setWeatherIcon(checkStrValidation(jWeather, "icon").getAsString());

        weather.setWindSpeed(checkNumValidation(jWind, "speed").getAsDouble());
        weather.setWindDegree(checkNumValidation(jWind, "deg").getAsDouble());

        weather.setClouds(checkNumValidation(jWeatherObject.getAsJsonObject("clouds"), "all").getAsInt());
        weather.setRainVolume(checkNumValidation(jWeatherObject.getAsJsonObject("rain"), "3h").getAsDouble());
        weather.setSnowVolume(checkNumValidation(jWeatherObject.getAsJsonObject("snow"), "3h").getAsDouble());

        return weather;
    }
}
