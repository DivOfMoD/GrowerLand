package com.example.nick.growerland.weatherapi;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ProviderTest {

    private Provider mProvider;

    @Before
    public void setUp() {
        mProvider = new Provider();
    }

    @Test
    public void doInBackgroundTest() throws Exception {
        double lat = 53.663735, lot = 23.825932; //Grondo location

        final CountDownLatch signal = new CountDownLatch(1);

        signal.await();// wait for callback
//        System.out.println(mProvider.getJSON(lat, lot));
    }


}