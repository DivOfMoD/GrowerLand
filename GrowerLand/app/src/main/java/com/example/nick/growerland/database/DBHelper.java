package com.example.nick.growerland.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(final Context context, final String dbName) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }
}
