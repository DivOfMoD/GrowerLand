package com.example.nick.growerland.database.models;

import com.example.nick.growerland.database.annotations.Table;
import com.example.nick.growerland.database.annotations.typeInteger;
import com.example.nick.growerland.database.annotations.typePrimaryKey;
import com.example.nick.growerland.database.annotations.typeString;

@Table(name = "ticketsTable")
public final class GardenModel {

    @typePrimaryKey
    @typeInteger
    public static final String ID = "_id";

    @typeString
    public static final String NAME = "name";

    @typeString
    public static final String PLACE = "place";

    @typeString
    public static final String LATITUDE = "latitude";

    @typeString
    public static final String LONGITUDE = "longitude";

}