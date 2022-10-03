package com.assignment.digikrafttechnicalassignment.Database;

public class db_tables {

    static final String TABLE_NAME_DATA = "Data";

    public  static final  String DATA_ID                = "_id";
    public  static final  String DATA_UNI_ID            = "uni_id";
    static  final  String DATA_FREE_RACKS               = "free_racks";
    static  final  String DATA_BIKES                    = "bikes";
    static  final  String DATA_LABEL                    = "label";
    static  final  String DATA_BIKE_RACKS               = "bike_racks";
    static  final  String DATA_UPDATED                  = "updated";
    static  final  String DATA_LATITUDE                 = "latitude";
    static  final  String DATA_LONGITUDE                = "longitude";

    static final String CREATE_TABLE_DATA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DATA
            + " ( " +
            DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DATA_UNI_ID + " TEXT UNIQUE, " +
            DATA_FREE_RACKS + " TEXT, " +
            DATA_BIKES + " TEXT, " +
            DATA_LABEL + " TEXT, " +
            DATA_BIKE_RACKS + " TEXT, " +
            DATA_UPDATED + " TEXT, " +
            DATA_LATITUDE + " TEXT, " +
            DATA_LONGITUDE + " TEXT" +
            ");";


}
