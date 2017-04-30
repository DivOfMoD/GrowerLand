package com.example.nick.growerland.database;

import android.content.Context;

public interface TableEditable<T> {

    final String DB_NAME = "db_name";

    void insert(Context context, T t);
    T read(Context context, int id);

}
