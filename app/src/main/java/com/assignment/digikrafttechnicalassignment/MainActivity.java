package com.assignment.digikrafttechnicalassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assignment.digikrafttechnicalassignment.Adapter.BikeListRecycleViewAdapter;
import com.assignment.digikrafttechnicalassignment.CallBack.OnClick;
import com.assignment.digikrafttechnicalassignment.Database.DBUtils;
import com.assignment.digikrafttechnicalassignment.NetWork.HandleApiResponse;
import com.assignment.digikrafttechnicalassignment.Object.Data;
import com.assignment.digikrafttechnicalassignment.Utils.GpsTracker;
import com.assignment.digikrafttechnicalassignment.Utils.Service;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClick {

    private GpsTracker gpsTracker;
    static double latitude ;
    static double longitude ;

    Context mContext;
    OnClick onClick;

    BikeListRecycleViewAdapter bikeListRecycleViewAdapter;
    RecyclerView bikeStationList ;
    ArrayList<Data>dataArrayList = new ArrayList<>();

    private ProgressBar progressBar;
    private RelativeLayout layout;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        onClick = this;
        bikeStationList = findViewById(R.id.bikeStationList);

        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        textView = (TextView) findViewById(R.id.textView);
        layout = findViewById(R.id.layout);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            layout.setVisibility(View.VISIBLE);
            Service.progressBar(progressStatus,handler,progressBar,textView);
            Service.getAllData(this);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                       MainActivity.this.finish();
                        System.exit(1);
                    }
                }).create().show();
    }

}