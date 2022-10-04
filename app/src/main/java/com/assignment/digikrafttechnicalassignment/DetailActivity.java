package com.assignment.digikrafttechnicalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.assignment.digikrafttechnicalassignment.Database.DBUtils;
import com.assignment.digikrafttechnicalassignment.Object.Data;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static String ID = "";
    private static Double LAT ;
    private static Double LON ;
    MapView mapView;
    private GoogleMap mMap;

    Button call;

    double latitude ;
    double longitude;

    Data data ;
    Context mContext;

    TextView bikeStationName, bikeStationDistance ,bikeQty,freeQty;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mContext = DetailActivity.this;
       ID = getIntent().getStringExtra("ID");
       LAT = Double.parseDouble(getIntent().getStringExtra("LAT"));
       LON = Double.parseDouble(getIntent().getStringExtra("LON"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       data = DBUtils.getDataByID(mContext,ID);

       latitude = data.getLatitude();
       longitude = data.getLongitude();

        bikeStationName = findViewById(R.id.bikeStationName);
        bikeStationDistance = findViewById(R.id.bikeStationDistance);
        bikeQty = findViewById(R.id.bikeQty);
        freeQty = findViewById(R.id.freeQty);

        bikeStationName.setText(data.getLabel());
        double distance = distance(LAT,LON,data.getLatitude(),data.getLongitude()) ;
        DecimalFormat df = new DecimalFormat("###,###");
        bikeStationDistance.setText(df.format(distance) + " Meter");
        bikeQty.setText(data.getBikes());
        freeQty.setText(data.getFree_racks());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        //  Log.e("Address","Address" +Address);

        //  Toast.makeText(getApplicationContext() ,Address,Toast.LENGTH_LONG).show();

        LatLng currentPosition = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(currentPosition)
                .title(data.getLabel() + "\n " + data.getBikes())).showInfoWindow();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,19));
        // googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(googleMap.getCameraPosition().zoom ));

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;

        if(dist != 0){
            return (dist);
        }else {
            return (0);
        }

    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}