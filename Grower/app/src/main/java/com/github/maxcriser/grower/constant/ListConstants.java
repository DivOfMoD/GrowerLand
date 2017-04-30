package com.github.maxcriser.grower.constant;

public final class ListConstants {

    public interface Database {

        String SQL_TABLE_CREATE_TEMPLATE = "CREATE TABLE IF NOT EXISTS %s (%s);";
        String SQL_TABLE_CREATE_FIELD_TEMPLATE = "%s %s%s";
        String mDatabaseName = "database.cards.thecriser";
        int dbVersion = 1;
    }

    public interface Extras {
        String EXTRA_GARDEN_NAME_CREATE = "extra_garden_name_create";
    }

    public static final String URL_JSON_SETTINGS = "http://growerlandnasa.appspot.com/";
    public static final String CONFIG = "config";

}