package com.assignment.digikrafttechnicalassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.assignment.digikrafttechnicalassignment.Adapter.BikeListRecycleViewAdapter;
import com.assignment.digikrafttechnicalassignment.CallBack.OnClick;
import com.assignment.digikrafttechnicalassignment.Database.DBUtils;
import com.assignment.digikrafttechnicalassignment.Object.Data;
import com.assignment.digikrafttechnicalassignment.Utils.GpsTracker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClick {

    private GpsTracker gpsTracker;
    static double latitude ;
    static double longitude ;

    Context mContext;
    OnClick onClick;

    BikeListRecycleViewAdapter bikeListRecycleViewAdapter;
    RecyclerView bikeStationList ;
    ArrayList<Data>dataArrayList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        onClick = this;
        bikeStationList = findViewById(R.id.bikeStationList);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        getLocation();

        dataArrayList = DBUtils.getAllData(this);

        LinearLayoutManager ListRecyclerView = new LinearLayoutManager(mContext); // (Context context)
        bikeListRecycleViewAdapter = new BikeListRecycleViewAdapter(mContext, dataArrayList ,latitude ,longitude,onClick);
        bikeStationList.setAdapter(bikeListRecycleViewAdapter);
        bikeStationList.setLayoutManager(ListRecyclerView);
        ListRecyclerView.setOrientation(LinearLayoutManager.VERTICAL);
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();

            Log.d("lat and log " , "lat" + latitude + "and " + "lon" + longitude);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onClick(String userID) {
        Intent intent = new Intent(getBaseContext(), DetailActivity.class);
        intent.putExtra("ID", userID);
        intent.putExtra("LAT", String.valueOf(latitude));
        intent.putExtra("LON", String.valueOf(longitude));
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}