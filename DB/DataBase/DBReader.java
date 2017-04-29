package com.divofmod.quizer.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public abstract class DBReader {

    public static ArrayList<String[]> read(SQLiteDatabase db, String tableName, String[] columnNames) {

        ArrayList<String[]> listOfValues = new ArrayList<>();

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                // делаем запрос всех данных из таблицы, получаем Cursor
                cursor = db.query(tableName, null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (cursor.moveToFirst())

                {

                    // определяем номера столбцов по имени в выборке
                    int[] idCols = new int[columnNames.length];

                    for (int i = 0; i < columnNames.length; i++)
                        idCols[i] = cursor.getColumnIndex(columnNames[i]);

                    String[] values = new String[idCols.length];

                    do {
                        // получаем значения по номерам столбцов

                        for (int i = 0; i < idCols.length; i++) {
                            values[i] = cursor.getString(idCols[i]);
                        }
                        listOfValues.add(values.clone());

                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        }
        return listOfValues;
    }

    public static String read(SQLiteDatabase db, String tableName, String columnName) {

        String temp = null;

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                // делаем запрос всех данных из таблицы, получаем Cursor
                cursor = db.query(tableName, null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (cursor.moveToFirst()) {
                    temp = cursor.getString(cursor.getColumnIndex(columnName));

                }
            }
            cursor.close();
        }

        return temp;
    }
}
