package com.example.nick.growerland.jsonprovider;

import java.util.concurrent.ExecutionException;

public class Provider {

    public String getJson(final String request) {
        final HttpClient httpClient = new HttpClient();
        httpClient.execute(request);
        String json = "";
        try {
            json = httpClient.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return json;
    }
}
