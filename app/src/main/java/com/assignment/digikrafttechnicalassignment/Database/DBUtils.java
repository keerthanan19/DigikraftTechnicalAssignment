package com.assignment.digikrafttechnicalassignment.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.assignment.digikrafttechnicalassignment.Object.Data;

import java.util.ArrayList;

public class DBUtils {

    private static db_manager db_manager;
    private static final String TAG = "DBUtils";

    public static boolean insert_data(Data data, Context mContext){
        boolean isSuccess = false;
        db_manager = new db_manager(mContext);
        try (SQLiteDatabase db = db_manager.getWritableDatabase()) {

            ContentValues cv = new ContentValues();
            cv.put(db_tables.DATA_UNI_ID, data.getId());
            cv.put(db_tables.DATA_FREE_RACKS, data.getFree_racks());
            cv.put(db_tables.DATA_BIKES, data.getBikes());
            cv.put(db_tables.DATA_LABEL, data.getLabel());
            cv.put(db_tables.DATA_BIKE_RACKS, data.getBike_racks());
            cv.put(db_tables.DATA_UPDATED, data.getUpdated());
            cv.put(db_tables.DATA_LATITUDE, data.getLatitude());
            cv.put(db_tables.DATA_LONGITUDE, data.getLongitude());

            try{
                long rowCount = db.insertOrThrow(db_tables.TABLE_NAME_DATA, null, cv);
                if (rowCount != -1){
                  //  Log.d(TAG, "insert_data " + " inserted "+ data.getParentId() + "  "+ data.getServiceName());
                    isSuccess = true;
                }else{
                    Log.e(TAG, "insert_data " + " insert failed");
                    isSuccess = false;
                }
            }catch (Exception e){
                try{
                    long rowCount = db.replaceOrThrow(db_tables.TABLE_NAME_DATA, null, cv);
                    if (rowCount != -1){
                        Log.d(TAG, "insert_data " +  data.getId() + " replaced");
                        isSuccess = true;
                    }else{
                        Log.e(TAG, "insert_data " + " replaced failed");
                        isSuccess = false;
                    }
                }catch (Exception ie){
                    Log.e(TAG, "insert_data " + " inserted or replaced failed  :"+ ie.getMessage());
                    isSuccess = false;
                }
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    @SuppressLint("Range")
    public static ArrayList<Data> getAllData(Context mContext) {
        ArrayList<Data>  dataArrayList = new ArrayList<>();
        db_manager = new db_manager(mContext);
        Cursor c;
        String qty = "0";

        StringBuilder query = new StringBuilder("SELECT * FROM " + db_tables.TABLE_NAME_DATA
        );

        try (SQLiteDatabase db = db_manager.getWritableDatabase()) {
            c = db.rawQuery(query.toString(), null);
            if (c.moveToFirst()) {
                do {
                    Data data = new Data();

                    data.setId(c.getString(c.getColumnIndex(db_tables.DATA_UNI_ID)));
                    data.setFree_racks(c.getString(c.getColumnIndex(db_tables.DATA_FREE_RACKS)));
                    data.setBikes(c.getString(c.getColumnIndex(db_tables.DATA_BIKES)));
                    data.setLabel(c.getString(c.getColumnIndex(db_tables.DATA_LABEL)));
                    data.setBike_racks(c.getString(c.getColumnIndex(db_tables.DATA_BIKE_RACKS)));
                    data.setUpdated(c.getString(c.getColumnIndex(db_tables.DATA_UPDATED)));
                    data.setLatitude(Double.parseDouble(c.getString(c.getColumnIndex(db_tables.DATA_LATITUDE))));
                    data.setLongitude(Double.parseDouble(c.getString(c.getColumnIndex(db_tables.DATA_LONGITUDE))));

                    dataArrayList.add(data);
                } while (c.moveToNext());
            }
            return dataArrayList;
        } catch (Exception e) {
            Log.e(TAG, "getAllData " + e);
        }
        return dataArrayList;
    }

    @SuppressLint("Range")
    public static Data getDataByID(Context mContext, String userID) {
        db_manager = new db_manager(mContext);
        Cursor c;
        String qty = "0";
        Data data = new Data();

        StringBuilder query = new StringBuilder("SELECT * FROM " + db_tables.TABLE_NAME_DATA
                + " where " + db_tables.DATA_UNI_ID + "='" + userID +"'"
        );

        try (SQLiteDatabase db = db_manager.getWritableDatabase()) {
            c = db.rawQuery(query.toString(), null);
            if (c.moveToFirst()) {
                do {

                    data.setId(c.getString(c.getColumnIndex(db_tables.DATA_UNI_ID)));
                    data.setFree_racks(c.getString(c.getColumnIndex(db_tables.DATA_FREE_RACKS)));
                    data.setBikes(c.getString(c.getColumnIndex(db_tables.DATA_BIKES)));
                    data.setLabel(c.getString(c.getColumnIndex(db_tables.DATA_LABEL)));
                    data.setBike_racks(c.getString(c.getColumnIndex(db_tables.DATA_BIKE_RACKS)));
                    data.setUpdated(c.getString(c.getColumnIndex(db_tables.DATA_UPDATED)));
                    data.setLatitude(Double.parseDouble(c.getString(c.getColumnIndex(db_tables.DATA_LATITUDE))));
                    data.setLongitude(Double.parseDouble(c.getString(c.getColumnIndex(db_tables.DATA_LONGITUDE))));

                } while (c.moveToNext());
            }
            return data;
        } catch (Exception e) {
            Log.e(TAG, "getDataByID " + e.toString());
        }
        return data;
    }



    public static void deleteAllData(Context context) {
        db_manager = new db_manager(context);
        SQLiteDatabase db = db_manager.getReadableDatabase();
        db.execSQL("delete from " + db_tables.TABLE_NAME_DATA);
    }


}
