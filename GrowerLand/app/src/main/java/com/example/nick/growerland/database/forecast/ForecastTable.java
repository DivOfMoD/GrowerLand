package com.example.nick.growerland.database.forecast;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nick.growerland.database.DBHelper;
import com.example.nick.growerland.database.TableEditable;

public class ForecastTable implements TableEditable<Forecast> {

    private SQLiteDatabase sqLiteDatabase;

    @Override
    public void insert(final Context context, final Forecast forecast) {
        sqLiteDatabase = new DBHelper(context,
                DB_NAME).getWritableDatabase();
        sqLiteDatabase.execSQL("create table if not exists " + "forecast"
                + " (id INTEGER, temperature REAL, humidity REAL, water_day REAL, top_dressing_day INTEGER);");
        try {
            sqLiteDatabase.execSQL("update " + "forecast"
                    + " set temperature = '" + forecast.getTemperature() + "' " + " humidity = '" + forecast.getHumidity() + "' "
                    + " water_day = '" + forecast.getWateringDaysLeft() + "' "  + " top_dressing_day = '" + forecast.getTopDressingDaysLeft() + "' "
                    + " where id = " + forecast.getId());
        } catch (final Exception e) {
            sqLiteDatabase.execSQL("insert into " + "forecast" + "(id, temperature, humidity, water_day, "
                    + "top_dressing_day)"
                    + "values('" + forecast.getId() + ", " + forecast.getTemperature() + ", "
                    + forecast.getHumidity() + ", " + forecast.getWateringDaysLeft() + ", " + forecast.getTopDressingDaysLeft() + "')");
        }
        sqLiteDatabase.close();
    }

    @Override
    public Forecast read(final Context context, final int id) {
        final Forecast temp = new Forecast();
        sqLiteDatabase = new DBHelper(context, DB_NAME).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + "forecast" + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor = sqLiteDatabase.query("forecast", null, null, null, null, null, null);
                cursor.moveToFirst();
                do {
                    if (id == Integer.parseInt(cursor.getString(0))) {
                        temp.setTemperature(Float.parseFloat(cursor.getString(1)));
                        temp.setHumidity(Float.parseFloat(cursor.getString(2)));
                        temp.setWateringDaysLeft(Float.parseFloat(cursor.getString(3)));
                        temp.setTopDressingDaysLeft(Integer.parseInt(cursor.getString(4)));
                        break;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return temp;
    }
}
