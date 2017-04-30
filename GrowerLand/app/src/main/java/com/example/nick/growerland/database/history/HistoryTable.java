package com.example.nick.growerland.database.history;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nick.growerland.database.DBHelper;
import com.example.nick.growerland.database.TableEditable;

public class HistoryTable implements TableEditable<History> {

    private SQLiteDatabase sqLiteDatabase;

    @Override
    public void insert(final Context context, final History history) {
        sqLiteDatabase = new DBHelper(context,
                DB_NAME).getWritableDatabase();
        sqLiteDatabase.execSQL("create table if not exists " + "history"
                + " (id INTEGER, date INTEGER, type TEXT);");

        sqLiteDatabase.execSQL("insert into " + "history" + "(id, date, type)"
                + " values('" + history.getId() + ", " + history.getTime() + ", "
                + history.getType() + "')");

        sqLiteDatabase.close();
    }

    @Override
    public History read(final Context context, final int id) {
        final History temp = new History();
        sqLiteDatabase = new DBHelper(context, DB_NAME).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + "history" + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor = sqLiteDatabase.query("history", null, null, null, null, null, null);
                cursor.moveToFirst();
                do {
                    if (id == Integer.parseInt(cursor.getString(0))) {
                        temp.setTime(Integer.parseInt(cursor.getString(1)));
                        temp.setType(cursor.getString(2));
                        break;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return temp;
    }
}
