package com.example.nick.growerland;

import android.app.Application;
import android.content.Context;

import com.example.nick.growerland.database.DatabaseHelper;

public class MyCore extends Application {

    private DatabaseHelper mDatabaseHelper;

    public DatabaseHelper getDatabaseHelper(final Context pContext) {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = DatabaseHelper.Impl.newInstance(pContext);
        }
        return mDatabaseHelper;
    }

    public MyCore() {
        ContextHolder.set(this);
    }

}
