package com.example.nick.growerland.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nick.growerland.R;
import com.example.nick.growerland.plantmanager.JsonTest;
import com.example.nick.growerland.plantmanager.PlantJsonProvider;
import com.example.nick.growerland.plantmanager.core.PlantCollection;
import com.example.nick.growerland.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    private static final long DURATION = 500;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final PlantCollection collection = new PlantJsonProvider().getPlantCollection(JsonTest.DATA);
        final boolean passed = PreferenceManager.getInstance().getPassedRegistration(this);

        new Handler().postDelayed(() -> {
            final Intent intent;
            if (passed) {
                intent = new Intent(this, MainActivity.class);
            } else {
                intent = new Intent(this, RegistrationActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, DURATION);
    }
}
