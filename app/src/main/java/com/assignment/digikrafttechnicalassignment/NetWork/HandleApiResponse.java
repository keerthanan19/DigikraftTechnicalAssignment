package com.assignment.digikrafttechnicalassignment.NetWork;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.assignment.digikrafttechnicalassignment.Database.DBUtils;
import com.assignment.digikrafttechnicalassignment.Object.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HandleApiResponse {

    private IpService mClient = null;
    List<Data> dataArrayList = new ArrayList<>();
    int count = 0;
    Context mContext;

    public HandleApiResponse(Context context, String url) {
        mContext = context;
        init(context,url);
    }

    private void init(Context context,String url) {
        mClient = new RetrofitClient().getClient(context,url).create(IpService.class);
    }

    public interface CallBackDataDelegate {
        void onResponseSuccess(List<Data> dataList);
        void onFailure(String error);
    }

    Data filterData(JSONArray jsonArray,String check,CallBackDataDelegate delegate) throws JSONException {
        dataArrayList = new ArrayList<>();
        for(int i=0;i<jsonArray.length(); i++){
            count+=1;
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Log.e("ccccccccccc ","loop "+jsonObject.toString());

            Data data = new Data();
            if(!jsonObject.isNull("id")){
                data.setId(jsonObject.getString("id"));
            }else {
                data.setId("");
            }
            if(!jsonObject.getJSONObject("properties").isNull("free_racks")){
                data.setFree_racks(jsonObject.getJSONObject("properties").getString("free_racks"));
            }else {
                data.setFree_racks("");
            }
            if(!jsonObject.getJSONObject("properties").isNull("bikes")){
                data.setBikes(jsonObject.getJSONObject("properties").getString("bikes"));
            }else {
                data.setBikes("");
            }
            if(!jsonObject.getJSONObject("properties").isNull("label")){
                data.setLabel(jsonObject.getJSONObject("properties").getString("label"));
            }else {
                data.setLabel("");
            }
            if(!jsonObject.getJSONObject("properties").isNull("bike_racks")){
                data.setBike_racks(jsonObject.getJSONObject("properties").getString("bike_racks"));
            }else {
                data.setBike_racks("");
            }
            if(!jsonObject.getJSONObject("properties").isNull("updated")){
                data.setUpdated(jsonObject.getJSONObject("properties").getString("updated"));
            }else {
                data.setUpdated("");
            }

            if(!jsonObject.getJSONObject("geometry").getJSONArray("coordinates").isNull(0)){
                data.setLatitude(jsonObject.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0));
            }else {
                data.setLatitude(0.0);
            }
            if(!jsonObject.getJSONObject("geometry").getJSONArray("coordinates").isNull(1)){
                data.setLongitude(jsonObject.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1));
            }else {
                data.setLongitude(0.0);
            }

            DBUtils.insert_data(data,mContext);
            dataArrayList.add(data);

            Log.e("filterData ","loop "+check + "  "+data.getLabel());


        }

        delegate.onResponseSuccess(dataArrayList);
        Log.e("filterData ","LIST SIZE "+dataArrayList.size() + " Count "+ count);


        return null;
    }


        public void getAllData(CallBackDataDelegate delegate){
            mClient.getAllData("pub_transport","stacje_rowerowe").enqueue(new Callback<JsonObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.code() == 200 ) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());// here response is your json string

                            filterData(jsonObject.getJSONArray("features"),"check111",delegate);
                            //  delegate.onResponseSuccess(dataArrayList);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("getAllData", "SOMETHING WENT WRONG");
                        try {
                            Log.e("getAllData", "response "+response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("getAllData", "onFailure: " + t);
                    delegate.onFailure(t.toString());
                }
            });
        }


}
