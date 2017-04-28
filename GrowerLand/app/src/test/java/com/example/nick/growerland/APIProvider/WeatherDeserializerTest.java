package com.example.nick.growerland.APIProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

public class WeatherDeserializerTest {

    String mWeatherJSON;

    @Before
    public void setUp() {
        mWeatherJSON = "{\"dt\":1493326800,\"main\":{\"temp\":274.16,\"temp_min\":274.16,\"temp_max\":276.355,\"" +
                "pressure\":1010.77,\"sea_level\":1030.43,\"grnd_level\":1010.77,\"humidity\":90,\"temp_kf\":-2.19}," +
                "\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"" +
                "clouds\":{\"all\":76},\"wind\":{\"speed\":1.51,\"deg\":108.003},\"rain\":{\"3h\":0.01},\"" +
                "sys\":{\"pod\":\"n\"},\"dt_txt\":\"2017-04-27 21:00:00\"}";
    }

    @Test
    public void deserialize() throws Exception {
        Gson gson = new GsonBuilder().
                registerTypeAdapter(WeatherDeserializer.class, new WeatherDeserializer())
                .create();
        Weather weather = gson.fromJson(mWeatherJSON, Weather.class);
    }

}