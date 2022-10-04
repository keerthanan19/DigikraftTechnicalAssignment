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
import com.assignment.digikrafttechnicalassignment.Utils.Controller;

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
        double distance = Controller.distance(lat,lon,dataArrayList.get(position).getLatitude(),dataArrayList.get(position).getLongitude()) ;
        DecimalFormat df = new DecimalFormat("###,###");
        holder.bikeStationDistance.setText(df.format(distance) + " Meter");
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
}