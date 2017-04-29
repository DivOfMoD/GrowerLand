package com.example.nick.growerland.async.task;

import com.example.nick.growerland.async.ProgressCallback;
import com.example.nick.growerland.async.Task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonReader implements Task<String, Void, String> {

    @Override
    public String doInBackground(final String pUrl, final ProgressCallback<Void> pVoidProgressCallback) throws Exception {
        final String resultJson;
        try {
            final URL url = new URL(pUrl);

            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            final InputStream inputStream = urlConnection.getInputStream();
            final StringBuilder buffer = new StringBuilder();

            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();
            inputStream.close();

        } catch (final Exception e) {
            throw new Exception(e);
        }
        return resultJson;
    }
}