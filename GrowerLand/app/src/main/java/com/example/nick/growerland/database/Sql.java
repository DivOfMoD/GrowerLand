package com.example.nick.growerland.database;

import com.example.nick.growerland.database.models.GardenModel;

import java.lang.reflect.AnnotatedElement;

public final class Sql {

    public static String getSqlWithQuery(final AnnotatedElement model) {
        return "SELECT * FROM " + DatabaseHelperImpl.getTableName(model) + " WHERE " + GardenModel.NAME + " LIKE ? ";
    }

    public static String getSqlAllItems(final AnnotatedElement model) {
        return "SELECT * FROM " + DatabaseHelperImpl.getTableName(model);
    }

}
