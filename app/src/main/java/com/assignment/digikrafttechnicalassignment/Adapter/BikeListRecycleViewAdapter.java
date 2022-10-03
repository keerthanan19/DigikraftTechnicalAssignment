package com.assignment.digikrafttechnicalassignment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.digikrafttechnicalassignment.CallBack.OnClick;
import com.assignment.digikrafttechnicalassignment.Object.Data;
import com.assignment.digikrafttechnicalassignment.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BikeListRecycleViewAdapter extends RecyclerView.Adapter<BikeListRecycleViewAdapter.RecycleViewHolder>{
    Context mContext;
    ArrayList<Data> dataArrayList;
    private final LayoutInflater inflater;

    Double lat ;
    Double lon ;

    OnClick onClick;

    public BikeListRecycleViewAdapter(Context context, ArrayList<Data> dataArrayList ,Double lat ,Double lon,OnClick onClick) {
        this.mContext = context;
        this.dataArrayList = dataArrayList;
        inflater = LayoutInflater.from(context);
        this.lat = lat;
        this.lon = lon ;
        this.onClick = onClick;

    }

    @NonNull
    @Override
    public BikeListRecycleViewAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bike_recycle_view_item, parent, false);
        return new BikeListRecycleViewAdapter.RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeListRecycleViewAdapter.RecycleViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.bikeStationName.setText(dataArrayList.get(position).getLabel());
        double distance = distance(lat,lon,dataArrayList.get(position).getLatitude(),dataArrayList.get(position).getLongitude()) ;
        DecimalFormat df = new DecimalFormat("###,###");
        holder.bikeStationDistance.setText(df.format(distance) + "m");
        holder.bikeQty.setText(dataArrayList.get(position).getBikes());
        holder.freeQty.setText(dataArrayList.get(position).getFree_racks());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(String.valueOf(dataArrayList.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    protected class RecycleViewHolder extends RecyclerView.ViewHolder {

        TextView bikeStationName, bikeStationDistance ,bikeQty,freeQty;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            bikeStationName = itemView.findViewById(R.id.bikeStationName);
            bikeStationDistance = itemView.findViewById(R.id.bikeStationDistance);
            bikeQty = itemView.findViewById(R.id.bikeQty);
            freeQty = itemView.findViewById(R.id.freeQty);

        }
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
        dist = dist * 60 * 1.1515;

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