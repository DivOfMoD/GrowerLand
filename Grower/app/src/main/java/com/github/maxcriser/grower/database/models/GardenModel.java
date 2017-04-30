package com.github.maxcriser.grower.database.models;

import com.github.maxcriser.grower.database.annotations.Table;
import com.github.maxcriser.grower.database.annotations.typeInteger;
import com.github.maxcriser.grower.database.annotations.typePrimaryKey;
import com.github.maxcriser.grower.database.annotations.typeString;

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

    @typeString
    public static final String VEG = "veg";

}