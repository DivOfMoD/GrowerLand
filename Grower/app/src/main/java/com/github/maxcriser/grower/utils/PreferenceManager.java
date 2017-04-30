package com.github.maxcriser.grower.utils;

import android.content.Context;
import android.content.SharedPreferences;

public final class PreferenceManager {

    private final String BASE = "base";
    private final String IS_REGISTRATION_PASSED = "is_registration_passed";

    private static final PreferenceManager ourInstance = new PreferenceManager();

    public static PreferenceManager getInstance() {
        return ourInstance;
    }

    private PreferenceManager() {
    }

    public boolean getPassedRegistration(final Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(BASE, Context.MODE_PRIVATE);
        return preferences.getBoolean(IS_REGISTRATION_PASSED, false);
    }

    public void setPassedRegistration(final Context context, final boolean b) {
        final SharedPreferences preferences = context.getSharedPreferences(BASE, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(IS_REGISTRATION_PASSED, b).apply();
    }
}
